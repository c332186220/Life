package com.cxl.life.app.layout.animal;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cxl.life.R;

/**
 * 多个动画同时执行
 */
public class Practice12PropertyValuesHolderLayout extends RelativeLayout {
    View view;
    Button animateBt;

    public Practice12PropertyValuesHolderLayout(Context context) {
        super(context);
    }

    public Practice12PropertyValuesHolderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PropertyValuesHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用 PropertyValuesHolder.ofFloat() 来创建不同属性的动画值方案
                // 第一个： scaleX 从 0 到 1
                // 第二个： scaleY 从 0 到 1
                // 第三个： alpha 从 0 到 1
                PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1);
                PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1);
                PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 1);

                // 然后，用 ObjectAnimator.ofPropertyValuesHolder() 把三个属性合并，创建 Animator 然后执行
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2, holder3);
                animator.start();
            }
        });
    }
}
