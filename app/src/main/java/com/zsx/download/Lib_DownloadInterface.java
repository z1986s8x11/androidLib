package com.zsx.download;

import android.content.Context;

import java.io.Serializable;

/**
 * 继承于此类的,因为要实现序列化，所有不能写成内部类,不能定义不可序列化的对象
 * 
 * @author zsx
 * @date 2015-03-31
 * 
 */
@Deprecated
public interface Lib_DownloadInterface extends Serializable{

	String getDownloadUrl();

	String getDownloadKey();

	String getSavePath();

	/**
	 * 下载完成时候会回调
	 */
	void doSuccess(Context context);

//	/**
//	 * 下载失败时候会回调
//	 */
//	void doError();
//
//	/**
//	 * 如果文件存在 是否用此文件返回
//	 * 
//	 * @param existFile
//	 * @return true 用此文件返回 false 删除文件重新下载
//	 */
//	boolean isUseExistDownloadFile(File existFile);

}
