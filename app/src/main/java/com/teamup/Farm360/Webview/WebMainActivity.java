package com.teamup.Farm360.Webview;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncDirectResponse;
import com.thefinestartist.finestwebview.FinestWebView;

public class WebMainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.web);
  }

  public void onClick(View view) {
    if (view.getId() == R.id.defaultTheme) {
      String url = AppSyncDirectResponse.getResponse("http://novoagri.in/Other/Dube/api_links.php?id=1");
      new FinestWebView.Builder(this).titleDefault("Wheat")
              .show(url);
      ////                    .toolbarScrollFlags(0)
      //                    .webViewJavaScriptEnabled(true)
      //                    .webViewUseWideViewPort(false)
      ////                    .show("http://andrewliu.in/2016/01/30/聊聊Redis的订阅发布/");
      //                    .show("http://www.youtube.com");
    } else if (view.getId() == R.id.redTheme) {
      //            Intent intent = new Intent(this, WebViewActivity.class);
      //            startActivity(intent);
      String url = AppSyncDirectResponse.getResponse("http://novoagri.in/Other/Dube/api_links.php?id=2");
      new FinestWebView.Builder(this).theme(R.style.RedTheme)
          .titleDefault("Bajra")
          .webViewBuiltInZoomControls(true)
          .webViewDisplayZoomControls(true)
          .dividerHeight(0)
          .gradientDivider(false)
          .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
              R.anim.activity_close_enter, R.anim.activity_close_exit)
          .injectJavaScript("javascript: document.getElementById('msg').innerHTML='Hello "
              + "TheFinestArtist"
              + "!';")
          .show(url);

    } else if (view.getId() == R.id.blueTheme) {
      String url = AppSyncDirectResponse.getResponse("http://novoagri.in/Other/Dube/api_links.php?id=3");
      new FinestWebView.Builder(this).theme(R.style.FinestWebViewTheme)
          .titleDefault("Corn/Maize")
          .showUrl(false)
          .statusBarColorRes(R.color.bluePrimaryDark)
          .toolbarColorRes(R.color.bluePrimary)
          .titleColorRes(R.color.finestWhite)
          .urlColorRes(R.color.bluePrimaryLight)
          .iconDefaultColorRes(R.color.finestWhite)
          .progressBarColorRes(R.color.finestWhite)
          .stringResCopiedToClipboard(R.string.copied_to_clipboard)
          .stringResCopiedToClipboard(R.string.copied_to_clipboard)
          .stringResCopiedToClipboard(R.string.copied_to_clipboard)
          .showSwipeRefreshLayout(true)
          .swipeRefreshColorRes(R.color.bluePrimaryDark)
          .menuSelector(R.drawable.selector_light_theme)
          .menuTextGravity(Gravity.CENTER)
          .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
          .dividerHeight(0)
          .gradientDivider(false)
          .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
          .show(url);
    } else if (view.getId() == R.id.blackTheme) {
      String url = AppSyncDirectResponse.getResponse("http://novoagri.in/Other/Dube/api_links.php?id=4");
      new FinestWebView.Builder(this).theme(R.style.FinestWebViewTheme)
          .titleDefault("Groundnuts")
          .toolbarScrollFlags(0)
          .statusBarColorRes(R.color.blackPrimaryDark)
          .toolbarColorRes(R.color.blackPrimary)
          .titleColorRes(R.color.finestWhite)
          .urlColorRes(R.color.blackPrimaryLight)
          .iconDefaultColorRes(R.color.finestWhite)
          .progressBarColorRes(R.color.finestWhite)
          .swipeRefreshColorRes(R.color.blackPrimaryDark)
          .menuSelector(R.drawable.selector_light_theme)
          .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
          .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
          .dividerHeight(0)
          .gradientDivider(false)
          //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
          .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
              R.anim.slide_right_out)
          //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
          .disableIconBack(true)
          .disableIconClose(true)
          .disableIconForward(true)
          .disableIconMenu(true)
          .show(url);
    }
  }
}
