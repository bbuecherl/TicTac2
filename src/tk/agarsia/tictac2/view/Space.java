package tk.agarsia.tictac2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class to support layout spacing.
 * 
 * Because androids Space view is not supported on APIs lower than 11, i
 * implemented this workaround to meet our support feature for APIs 9 and up.
 * This class is used for adding white spaces in layouts
 * 
 * 
 * @author agarsia (Bernhard Bücherl)
 * 
 */
public class Space extends View {

	/**
	 * Default android view constructor
	 * 
	 * This creates a blank view.
	 * 
	 * @param context
	 *            context object of the application
	 * @param attrs
	 *            attribute set of the view (defined in xml)
	 */
	public Space(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}