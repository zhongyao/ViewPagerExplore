package com.hongri.viewpager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.SubscriptSpan;
import android.widget.TextView;
import com.hongri.viewpager.R;

/**
 * @author hongri
 * SpannableStringBuilder调研Activity
 */
public class SpannableActivity extends AppCompatActivity {

    private TextView tv1;
    private String content = "富兰克林是个伟大的科学家";
    private String content22 = "22富兰克林是个伟大的科学家";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);

        tv1 = findViewById(R.id.tv1);

        /**
         * 部分字体变色
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //ForegroundColorSpan span = new ForegroundColorSpan(ResHelper.getColor(R.color.colorAccent));
        //builder.setSpan(span,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 部分背景变色
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //BackgroundColorSpan span = new BackgroundColorSpan(ResHelper.getColor(R.color.colorAccent));
        //builder.setSpan(span,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 部分字体变大且颜色不同
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //ForegroundColorSpan span = new ForegroundColorSpan(ResHelper.getColor(R.color.colorAccent));
        //AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(33, true);
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //builder.setSpan(sizeSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 文本可点击，有点击事件
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //ClickableSpan clickableSpan = new ClickableSpan() {
        //    @Override
        //    public void onClick(View widget) {
        //        ToastUtil.showToast(SpannableActivity.this,"点击事件");
        //
        //    }
        //
        //    @Override
        //    public void updateDrawState(TextPaint ds) {
        //        Logger.d("updateDrawState");
        //        //super.updateDrawState(ds);
        //        ds.setUnderlineText(true);
        //    }
        //};
        //builder.setSpan(clickableSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setMovementMethod(LinkMovementMethod.getInstance());
        //tv1.setText(builder);

        /**
         * 模糊效果
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //MaskFilter filter = new BlurMaskFilter(4.0f, Blur.OUTER);
        //MaskFilterSpan span = new MaskFilterSpan(filter);
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 删除线效果
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //StrikethroughSpan span = new StrikethroughSpan();
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 下划线效果
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //UnderlineSpan span = new UnderlineSpan();
        //builder.setSpan(span,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 设置图片基于底部
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //DynamicDrawableSpan span = new DynamicDrawableSpan() {
        //    @Override
        //    public Drawable getDrawable() {
        //        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
        //        drawable.setBounds(0, 0, 100, 100);
        //        return drawable;
        //    }
        //};
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 设置图片
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
        //drawable.setBounds(0,0,100,100);
        //ImageSpan span = new ImageSpan(drawable);
        //builder.setSpan(span,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 基于X的缩放
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //ScaleXSpan span = new ScaleXSpan(3.0f);
        //builder.setSpan(span,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 字体样式 粗体/斜体等
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        ////StyleSpan span = new StyleSpan(Typeface.BOLD);
        //StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 上标/下标
         */
        SpannableStringBuilder builder = new SpannableStringBuilder(content22);
        //上标
        //SuperscriptSpan span = new SuperscriptSpan();
        //下标
        SubscriptSpan span = new SubscriptSpan();
        builder.setSpan(span,1,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.setText(builder);

        /**
         * 字体、大小、样式和颜色
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //TextAppearanceSpan span = new TextAppearanceSpan(this, R.style.TextAppearance_AppCompat_Medium);
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 文本字体
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //TypefaceSpan span = new TypefaceSpan("monospace");
        //builder.setSpan(span, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setText(builder);

        /**
         * 文字超链接
         */
        //SpannableStringBuilder builder = new SpannableStringBuilder(content);
        //URLSpan span = new URLSpan("http://www.baidu.com");
        //builder.setSpan(span,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv1.setMovementMethod(new LinkMovementMethod());
        //tv1.setText(builder);
    }
}
