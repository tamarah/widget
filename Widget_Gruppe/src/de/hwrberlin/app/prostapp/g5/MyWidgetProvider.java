package de.hwrberlin.app.prostapp.g5;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import de.vogella.android.widget.example.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

  private static final String ACTION_CLICK = "ACTION_CLICK";
  // define BroadcastReceiver
  private BroadcastReceiver WidgetBroadCastReceiver = new BroadcastReceiver(){
	@Override
	public void onReceive(Context context, Intent intent) {
		
	}
  };

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

	  String[][] eventlist = get_testdata();
	  
	  
    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        MyWidgetProvider.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {
      // Create some random data
      int number = (new Random().nextInt(10000));
      
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.widget_layout);
      Log.w("WidgetExample", String.valueOf(number));
      // get the current Date - only year-month-day
      Calendar c = Calendar.getInstance();
      System.out.println("Current time => "+c.getTime());
      
//      LinearLayout Linear_ContentLayout = appWidgetManager.;
      
      SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
      String formattedDate = df.format(c.getTime());
      // Set the text
      //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
      remoteViews.setTextViewText(R.id.widget_date, "Termine für den "+formattedDate+":");
      remoteViews.setTextViewText(R.id.widget_event1_info, "Web-Entwicklung");
      remoteViews.setTextViewText(R.id.widget_event1_time, "14:00 - 17:00");
      remoteViews.setTextViewText(R.id.widget_event1_info, "Raum: 307");
      // Register an onClickListener
      
      Intent intent = new Intent(context, MyWidgetProvider.class);

      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE); 
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      remoteViews.setOnClickPendingIntent(R.id.widget_date, pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
      
      
      // Add Broadcastreceiver
      context.getApplicationContext().registerReceiver(this.WidgetBroadCastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }
  }
  
  private String[][] get_testdata(){
	  String[][] eventlist = new String[3][3];
	  
	  for(int i=1;i<=(int)(Math.random()*10); i++){
		  eventlist[i-1][0] = "Kurs "+i;
//		  int hour = ;
//		  int minute = (int)(Math.random()*60);
		  eventlist[i-1][1] = String.valueOf((int)(Math.random()*24));
		  eventlist[i-1][2] = String.valueOf((int)(Math.random()*60));
	  }
	  return eventlist;
  }
} 