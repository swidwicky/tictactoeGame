package com.example.tictactoegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class TicTacToeSquare extends View {
    private Bitmap xBitmap;
    private Bitmap oBitmap;
    private boolean isXSymbol = false;
    private boolean isOSymbol = false;

    // Define an enum to represent the symbol
    public enum Symbol {
        NONE,
        X,
        O
    }

    public TicTacToeSquare(Context context) {
        super(context);
        init();
    }

    public TicTacToeSquare(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TicTacToeSquare(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialize your X and O bitmaps here
        // You can load them from resources or create them programmatically
        // Example:
        xBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_x);
        oBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_o);
    }

    public void setXSymbol() {
        isXSymbol = true;
        isOSymbol = false;
        invalidate(); // Redraw the view when the symbol changes
    }

    public void setOSymbol() {
        isOSymbol = true;
        isXSymbol = false;
        invalidate(); // Redraw the view when the symbol changes
    }

    public boolean isSymbolSet() {
        return isXSymbol || isOSymbol;
    }

    public boolean isFilled() {
        return isXSymbol || isOSymbol;
    }

    public boolean isXSymbol() {
        return isXSymbol;
    }

    public boolean isOSymbol() {
        return isOSymbol;
    }

    public void clearCell() {
        isXSymbol = false;
        isOSymbol = false;
        invalidate(); // Redraw the view when the symbol is cleared
    }

    // New method to get the symbol
    public Symbol getSymbol() {
        if (isXSymbol) {
            return Symbol.X;
        } else if (isOSymbol) {
            return Symbol.O;
        } else {
            return Symbol.NONE;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int symbolSize = Math.min(width, height); // Use the smaller dimension

        if (isXSymbol) {
            // Draw X symbol centered in the cell
            if (xBitmap != null) {
                int left = (width - xBitmap.getWidth()) / 2;
                int top = (height - xBitmap.getHeight()) / 2;
                canvas.drawBitmap(xBitmap, left, top, null);
            }
        } else if (isOSymbol) {
            // Draw O symbol centered in the cell
            if (oBitmap != null) {
                int left = (width - oBitmap.getWidth()) / 2;
                int top = (height - oBitmap.getHeight()) / 2;
                canvas.drawBitmap(oBitmap, left, top, null);
            }
        }
    }
}
