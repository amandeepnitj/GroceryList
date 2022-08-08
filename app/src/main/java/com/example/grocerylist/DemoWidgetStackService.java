package com.example.grocerylist;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class DemoWidgetStackService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DemoWidgetStackViewsfactory(this.getApplicationContext());
    }
}

class DemoWidgetStackViewsfactory implements RemoteViewsService.RemoteViewsFactory{
    public Context context;
    public static int numimages =3;



    private int appWidgetid;
    SQLitehelper sqLitehelper;

    private ArrayList<String> exampleData ;

    public DemoWidgetStackViewsfactory(Context context) {
        this.context = context;
        sqLitehelper = new SQLitehelper(this.context);
        exampleData = sqLitehelper.getfavouritelistitemsonly();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return exampleData.size();

    }

    @Override
    public RemoteViews getViewAt(int i) {
//        RemoteViews view = new RemoteViews(context.getPackageName(),R.layout.stack_item);
//        String image_id = "img_"+i;
//        view.setImageViewResource(R.id.stack_image,context.getResources().getIdentifier(image_id,"drawable",context.getPackageName()));
////        view.setImageViewResource(R.id.stack_image,R.drawable.example_appwidget_preview);
//        return view;


        // new for this

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.example_widget_item);
        views.setTextViewText(R.id.example_widget_item_text, exampleData.get(i));
        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
