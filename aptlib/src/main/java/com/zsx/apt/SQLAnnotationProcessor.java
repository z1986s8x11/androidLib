package com.zsx.apt;

import com.zsx.apt.database.Lib_SQL;
import com.zsx.apt.database.SQLDefaultEnum;
import com.zsx.apt.database.SQLFieldEnum;
import com.zsx.apt.database.SQLKeyEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes(value = {"com.zsx.apt.database.Lib_SQL"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SQLAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 获得被该注解声明的元素
        Set<? extends Element> elememts = roundEnv.getElementsAnnotatedWith(Lib_SQL.class);
        TypeElement classElement = null;// 声明类元素
        List<VariableElement> fields = null;// 声明一个存放成员变量的列表
        // 存放二者
        Map<String, List<VariableElement>> maps = new LinkedHashMap<String, List<VariableElement>>();
        // 遍历
        for (Element ele : elememts) {
            // 判断该元素是否为类
            if (ele.getKind() == ElementKind.CLASS) {
                classElement = (TypeElement) ele;
                maps.put(classElement.getQualifiedName().toString(), fields = new ArrayList<VariableElement>());

            } else if (ele.getKind() == ElementKind.FIELD) // 判断该元素是否为成员变量
            {
                VariableElement varELe = (VariableElement) ele;
                // 获取该元素封装类型
                TypeElement enclosingElement = (TypeElement) varELe.getEnclosingElement();
                // 拿到key
                String key = enclosingElement.getQualifiedName().toString();
                fields = maps.get(key);
                if (fields == null) {
                    maps.put(key, fields = new ArrayList<VariableElement>());
                }
                fields.add(varELe);
            }
        }
        for (String key : maps.keySet()) {
            if (maps.get(key).size() == 0) {
                TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(key);
                List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(typeElement);
                if (allMembers.size() > 0) {
                    maps.get(key).addAll(ElementFilter.fieldsIn(allMembers));
                }
            }
        }
        try {
            generateCodes(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void generateCodes(Map<String, List<VariableElement>> maps) throws IOException {
        // 遍历map
        for (String key : maps.keySet()) {
            List<VariableElement> fields = maps.get(key);
            String typeName = key.substring(key.lastIndexOf(".") + 1, key.length());
            JavaFileObject f = processingEnv.getFiler().createSourceFile(key + "_Auto");
            Writer w;
            PrintWriter pw = null;
            try {
                w = f.openWriter();
                pw = new PrintWriter(w);
                // pw.println("/**");
                int lastIndex = key.lastIndexOf(".");
                if (lastIndex != -1) {
                    pw.println("package " + key.substring(0, lastIndex) + ";");
                }
                pw.println();
                pw.println("import java.util.ArrayList;");
                pw.println("import java.util.List;");
                pw.println();
                pw.println("public class " + typeName + "_Auto {");
                /*************************** 创建常量 *************************************/
                pw.println("	public static final String TABLE_NAME = \"" + typeName + "\";");
                pw.println();
                /** =========================建表SQL 方法========================= **/
                pw.println("	public static String createSQL(){");
                StringBuilder createSB = new StringBuilder();
                createSB.append("CREATE TABLE IF NOT EXISTS " + typeName + "(");
                for (int i = 0; i < fields.size(); i++) {
                    VariableElement field = fields.get(i);
                    Lib_SQL sql = field.getAnnotation(Lib_SQL.class);
                    if (!sql._isCreate()) {
                        continue;
                    }
                    if (sql._key() == SQLKeyEnum.PRIMARY_KEY_AUTOINCREMENT) {
                        if (sql._type() != SQLFieldEnum.Integer) {
                            processingEnv.getMessager().printMessage(Kind.ERROR, "必须为SQLFieldEnum.Integer类型", field);
                        }
                    }
                    createSB.append(field.getSimpleName());
                    createSB.append(" ");
                    createSB.append(sql._type());
                    if (sql._defaultValue() != SQLDefaultEnum.DEFAULT) {
                        createSB.append(" DEFAULT ");
                        createSB.append(sql._defaultValue());
                    }
                    if (sql._key() != SQLKeyEnum.DEFAULT) {
                        createSB.append(" ");
                        createSB.append(sql._key());
                    }
                    createSB.append(",");
                }
                if (',' == createSB.charAt(createSB.length() - 1)) {
                    createSB.delete(createSB.length() - 1, createSB.length());
                }
                createSB.append(")");
                pw.println("		String createSQL = \"" + createSB.toString() + "\";");
                pw.println("		return createSQL;");
                pw.println("	}");
                /** =========================插入SQL 方法========================= **/
                pw.println("	public static String  insertSQL(List<" + key + "> objs){");
                pw.println("		StringBuffer sb = new StringBuffer();");
                pw.println("		sb.append(\"insert into \");");
                pw.println("		sb.append(\"" + typeName + "(\");");
                for (int i = 0; i < fields.size(); i++) {
                    VariableElement field = fields.get(i);
                    Lib_SQL str = field.getAnnotation(Lib_SQL.class);
                    if (!str._isCreate() || !str._isInsert()) {
                        continue;
                    }
                    if (str._key() == SQLKeyEnum.PRIMARY_KEY_AUTOINCREMENT) {
                        continue;
                    }
                    pw.println("		sb.append(\"" + field.getSimpleName() + "\");");
                    pw.println("		sb.append(\",\");");
                }
                pw.println("		if (',' == sb.charAt(sb.length() - 1)) {");
                pw.println("			sb.delete(sb.length() - 1, sb.length());");
                pw.println("		}");
                pw.println("		sb.append(\") values \");");
                pw.println("		for (int i = 0; i < objs.size(); i++) {");
                pw.println("			sb.append(\"(\");");
                pw.println("			" + key + " obj = objs.get(i);");
                for (int i = 0; i < fields.size(); i++) {
                    VariableElement field = fields.get(i);
                    Lib_SQL str = field.getAnnotation(Lib_SQL.class);
                    if (!str._isCreate() || !str._isInsert()) {
                        continue;
                    }
                    if (str._key() == SQLKeyEnum.PRIMARY_KEY_AUTOINCREMENT) {
                        continue;
                    }
                    switch (str._type()) {
                        case Boolean:
                            pw.println("			sb.append(obj." + field.getSimpleName() + "? 1 : 0);");
                            break;
                        case String:
                            pw.println("			if(obj." + field.getSimpleName() + " != null){");
                            pw.println("				sb.append(\"'\"+obj." + field.getSimpleName() + "+\"'\");");
                            pw.println("			}else{");
                            pw.println("				sb.append(String.valueOf(obj." + field.getSimpleName() + "));");
                            pw.println("			}");
                            break;
                        case Integer:
                        case Float:
                        case Long:
                            pw.println("			sb.append(obj." + field.getSimpleName() + ");");
                            break;
                    }

                    if (i != fields.size() - 1) {
                        pw.println("			sb.append(\",\");");
                    }
                }
                pw.println("			if (',' == sb.charAt(sb.length() - 1)) {");
                pw.println("				sb.delete(sb.length() - 1, sb.length());");
                pw.println("			}");
                pw.println("			sb.append(\")\");");
                pw.println("			if (i != objs.size() - 1) {");
                pw.println("				sb.append(\",\");");
                pw.println("			}");
                pw.println("		}");
                pw.println("		sb.append(\";\");");
                pw.println("		return sb.toString();");
                pw.println("	}");
                pw.println();
                pw.println("	public static String  insertSQL(" + key + " obj){");
                pw.println("		return insertSQL(java.util.Arrays.asList(obj));");
                pw.println("	}");
                pw.println();
                /******************************* 查询 ****************************/
                pw.println("	public static List<" + key + ">  selectSQL(android.database.Cursor cursor){");
                pw.println("		List<" + key + "> list = new ArrayList<" + key + ">();");
                pw.println("		while (cursor.moveToNext()) {");
                pw.println("			" + key + " bean = new " + key + "();");
                for (int i = 0; i < fields.size(); i++) {
                    VariableElement field = fields.get(i);
                    Lib_SQL str = field.getAnnotation(Lib_SQL.class);
                    if (!str._isSelect() || !str._isCreate()) {
                        continue;
                    }
                    String type = field.asType().toString();
                    if ("java.lang.String".equals(type)) {
                        type = "String";
                        pw.println("			bean." + field.getSimpleName() + "=cursor.get" + type + "(cursor.getColumnIndex(\"" + field.getSimpleName() + "\"));");
                    } else if ("boolean".equals(type)) {
                        pw.println("			bean." + field.getSimpleName() + "=(cursor.getInt(cursor.getColumnIndex(\"" + field.getSimpleName() + "\"))==1);");
                    } else {
                        StringBuilder typeSb = new StringBuilder(type);
                        typeSb.setCharAt(0, Character.toUpperCase(type.charAt(0)));
                        type = typeSb.toString();
                        pw.println("			bean." + field.getSimpleName() + "=cursor.get" + type + "(cursor.getColumnIndex(\"" + field.getSimpleName() + "\"));");
                    }

                }
                pw.println("			list.add(bean);");
                pw.println("		}");
                pw.println("		cursor.close();");
                pw.println("		return list;");
                pw.println("	}");
                pw.println("}");
                // pw.println("*/");
                pw.flush();
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        }
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
