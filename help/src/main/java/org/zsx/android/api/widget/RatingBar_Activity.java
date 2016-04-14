package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingBar_Activity extends _BaseActivity implements
		RatingBar.OnRatingBarChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_ratingbar);
		RatingBar mRatingBar = (RatingBar) findViewById(R.id.act_widget_current_view);
		mRatingBar.setOnRatingBarChangeListener(this);
		// mRatingBar.setRating(rating);
		// mRatingBar.setNumStars(numStars);
		// mRatingBar.setStepSize(stepSize);
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		Toast.makeText(this, "您选择了" + rating, Toast.LENGTH_SHORT).show();
	}
}
