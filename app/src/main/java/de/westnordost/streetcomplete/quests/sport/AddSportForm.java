package de.westnordost.streetcomplete.quests.sport;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import de.westnordost.streetcomplete.R;
import de.westnordost.streetcomplete.quests.AbstractQuestFormAnswerFragment;
import de.westnordost.streetcomplete.view.ImageSelectAdapter;

public class AddSportForm extends AbstractQuestFormAnswerFragment
{
	public static final String SPORT = "sport";

	private static final String SELECTED_INDEX = "selected_item";

	private static final int MORE_THAN_95_PERCENT_COVERED = 8;

	private ImageSelectAdapter imageSelector;

	private static final SportValue[] SPORTS_VALUES = new SportValue[]{
            new SportValue("soccer",			R.drawable.ic_roof_gabled),
            new SportValue("tennis",			R.drawable.ic_roof_gabled),
            new SportValue("baseball",			R.drawable.ic_roof_gabled),
            new SportValue("basketball",		R.drawable.ic_roof_gabled),
            new SportValue("golf",			    R.drawable.ic_roof_gabled),
            new SportValue("equestrian",		R.drawable.ic_roof_gabled),
            new SportValue("athletics",			R.drawable.ic_roof_gabled),
            new SportValue("volleyball",		R.drawable.ic_roof_gabled),
            new SportValue("beachvolleyball",	R.drawable.ic_roof_gabled),
            new SportValue("american_football",	R.drawable.ic_roof_gabled),
            new SportValue("skateboard",		R.drawable.ic_roof_gabled),
            new SportValue("bowls",			    R.drawable.ic_roof_gabled),
            new SportValue("boules",			R.drawable.ic_roof_gabled),
            new SportValue("shooting",			R.drawable.ic_roof_gabled),
            new SportValue("cricket",			R.drawable.ic_roof_gabled),
            new SportValue("table_tennis",		R.drawable.ic_roof_gabled),
            new SportValue("gymnastics",		R.drawable.ic_roof_gabled)
			};

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		setTitle(R.string.quest_sport_title);

		View contentView = setContentView(R.layout.quest_generic_list);

		final RecyclerView sportList = (RecyclerView) contentView.findViewById(R.id.listSelect);
		GridLayoutManager lm = new GridLayoutManager(getActivity(), 4);
		sportList.setLayoutManager(lm);

		final List<ImageSelectAdapter.Item> sportValuesList = Arrays.<ImageSelectAdapter.Item>asList(SPORTS_VALUES);

		imageSelector = new ImageSelectAdapter();
		imageSelector.setItems(sportValuesList.subList(0,MORE_THAN_95_PERCENT_COVERED));
		if(savedInstanceState != null)
		{
			imageSelector.setSelectedIndex(savedInstanceState.getInt(SELECTED_INDEX, -1));
		}
		sportList.setAdapter(imageSelector);
		sportList.setNestedScrollingEnabled(false);

		final Button showMoreButton = (Button) contentView.findViewById(R.id.buttonShowMore);
		showMoreButton.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				imageSelector.add(sportValuesList.subList(MORE_THAN_95_PERCENT_COVERED, sportValuesList.size()));
				showMoreButton.setVisibility(View.GONE);
			}
		});

		return view;
	}

	@Override public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt(SELECTED_INDEX, imageSelector.getSelectedIndex());
	}

	@Override protected void onClickOk()
	{
		Bundle answer = new Bundle();
		Integer selectedIndex = imageSelector.getSelectedIndex();
		if(selectedIndex != -1)
		{
			String osmValue = SPORTS_VALUES[selectedIndex].osmValue;
			answer.putString(SPORT, osmValue);
		}
		applyFormAnswer(answer);
	}

	@Override protected List<Integer> getOtherAnswerResourceIds()
	{
		List<Integer> answers = super.getOtherAnswerResourceIds();
		answers.add(R.string.quest_sport_answer_multi);
		return answers;
	}

	@Override protected boolean onClickOtherAnswer(int itemResourceId)
	{
		if(super.onClickOtherAnswer(itemResourceId)) return true;

		if(itemResourceId == R.string.quest_sport_answer_multi)
		{
			Bundle answer = new Bundle();
			answer.putString(SPORT, "multi");
			applyImmediateAnswer(answer);
			return true;
		}

		return false;
	}

	@Override public boolean hasChanges()
	{
		return imageSelector.getSelectedIndex() != -1;
	}

	private static class SportValue extends ImageSelectAdapter.Item
	{
		public final String osmValue;

		public SportValue(String osmValue, int drawableId)
		{
			super(drawableId, -1);
			this.osmValue = osmValue;
		}
	}
}