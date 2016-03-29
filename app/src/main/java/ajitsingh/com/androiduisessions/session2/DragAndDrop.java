package ajitsingh.com.androiduisessions.session2;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import ajitsingh.com.androiduisessions.R;

public class DragAndDrop extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.drag_and_drop);

    TextView dragView = (TextView) findViewById(R.id.drag_text);
    TextView dropView = (TextView) findViewById(R.id.drop_text);

    dragView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        v.startDrag(ClipData.newPlainText("Drag Label", "Drag Text"), new DragShadow(v), v, 0);
        return true;
      }
    });

    dropView.setOnDragListener(new View.OnDragListener() {
      @Override
      public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (action) {
          case DragEvent.ACTION_DROP:
            TextView targetTextView = (TextView) v;
            TextView droppedTextView = (TextView) event.getLocalState();
            targetTextView.setText("Drag Complete: " + droppedTextView.getText());
            return true;
        }

        return true;
      }
    });
  }

  static class DragShadow extends View.DragShadowBuilder {
    private View view;

    public DragShadow(View view) {
      super(view);
      this.view = view;
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
      View view = getView();

      shadowSize.set(view.getWidth(), view.getHeight());
      shadowTouchPoint.set(view.getWidth()/2, view.getHeight()/2);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
      view.draw(canvas);
    }
  }
}
