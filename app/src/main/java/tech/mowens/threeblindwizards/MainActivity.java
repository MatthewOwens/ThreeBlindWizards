package tech.mowens.threeblindwizards;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public enum ButtonType {
        INCREMENT(0),
        DECREMENT(1);

        public final int index;

        ButtonType(int index) {
            this.index = index;
        }
    }

    public enum StatTypes {
        WAVE(0),
        GROWTH(1),
        BLAZE(2),
        HEALTH(3);

        public final int index;

        StatTypes(int index) {
            this.index = index;
        }
    }

    private Stat stats[] = new Stat[4];
    private Button createButton;
    private Button noiseButton;

    private TextView name;
    private TextView ability;
    private ImageView sprite;

    private final int startingHealth = 25;

    private Vector<String> nameArr = new Vector<String>();
    private Vector<String> abilityArr = new Vector<String>();
    //private final MediaPlayer mp = MediaPlayer.create(getResources(), R.raw.beep);
    private MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        populateStrings();
        mp = MediaPlayer.create(this, R.raw.beep);

        // Initilizing the stats
        for(int i = 0; i < 4; ++i)
            stats[i] = new Stat();

        stats[StatTypes.WAVE.index].val = 0;
        stats[StatTypes.WAVE.index].textView = (TextView) findViewById(R.id.wTextView);
        stats[StatTypes.WAVE.index].buttons[ButtonType.INCREMENT.index] = (ImageButton) findViewById(R.id.wIncreaseButton);
        stats[StatTypes.WAVE.index].buttons[ButtonType.DECREMENT.index] = (ImageButton) findViewById(R.id.wDecreaseButton);

        stats[StatTypes.GROWTH.index].val = 0;
        stats[StatTypes.GROWTH.index].textView = (TextView) findViewById(R.id.gTextView);
        stats[StatTypes.GROWTH.index].buttons[ButtonType.INCREMENT.index] = (ImageButton) findViewById(R.id.gIncreaseButton);
        stats[StatTypes.GROWTH.index].buttons[ButtonType.DECREMENT.index] = (ImageButton) findViewById(R.id.gDecreaseButton);

        stats[StatTypes.BLAZE.index].val = 0;
        stats[StatTypes.BLAZE.index].textView = (TextView) findViewById(R.id.bTextView);
        stats[StatTypes.BLAZE.index].buttons[ButtonType.INCREMENT.index] = (ImageButton) findViewById(R.id.bIncreaseButton);
        stats[StatTypes.BLAZE.index].buttons[ButtonType.DECREMENT.index] = (ImageButton) findViewById(R.id.bDecreaseButton);

        stats[StatTypes.HEALTH.index].val = 0;
        stats[StatTypes.HEALTH.index].textView = (TextView) findViewById(R.id.hTextView);
        stats[StatTypes.HEALTH.index].buttons[ButtonType.INCREMENT.index] = (ImageButton) findViewById(R.id.hIncreaseButton);
        stats[StatTypes.HEALTH.index].buttons[ButtonType.DECREMENT.index] = (ImageButton) findViewById(R.id.hDecreaseButton);

        // Initilizing the misc buttons
        createButton = (Button) findViewById(R.id.genCharaButton);
        createButton.setOnClickListener(this);

        noiseButton = (Button) findViewById(R.id.noiseButton);
        noiseButton.setOnClickListener(this);

        // Health stuff


        name = (TextView) findViewById(R.id.charaTextView);
        sprite = (ImageView) findViewById(R.id.charaImageView);
        ability = (TextView) findViewById(R.id.abilityTextView);

        // Setting the buttons as clickable
        stats[StatTypes.WAVE.index].buttons[0].setOnClickListener(this);
        stats[StatTypes.WAVE.index].buttons[1].setOnClickListener(this);
        stats[StatTypes.GROWTH.index].buttons[0].setOnClickListener(this);
        stats[StatTypes.GROWTH.index].buttons[1].setOnClickListener(this);
        stats[StatTypes.BLAZE.index].buttons[0].setOnClickListener(this);
        stats[StatTypes.BLAZE.index].buttons[1].setOnClickListener(this);
        stats[StatTypes.HEALTH.index].buttons[0].setOnClickListener(this);
        stats[StatTypes.HEALTH.index].buttons[1].setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v == createButton)
            genCharacter();
        else if (v == stats[StatTypes.HEALTH.index].buttons[ButtonType.DECREMENT.index])
            modifyHealth(-1);
        else if (v == stats[StatTypes.HEALTH.index].buttons[ButtonType.INCREMENT.index])
            modifyHealth(1);

        for(int i = 0; i < 3; ++i)
            stats[i].update(v);

        mp.seekTo(0);
        mp.start();
    }

    private void genCharacter()
    {
        Random rand = new Random();
        Log.i("MAIN", "genCharacter");
        genStatValues();

        for(int i = 0; i < 3; ++i)
            stats[i].textView.setText(Integer.toString(stats[i].val));

        modifyHealth(startingHealth - stats[StatTypes.HEALTH.index].val);

        name.setText(nameArr.get(rand.nextInt(nameArr.size())));
        ability.setText(abilityArr.get(rand.nextInt(abilityArr.size())));
    }

    private void populateStrings()
    {
        nameArr.add("name0");
        nameArr.add("name1");
        nameArr.add("name2");
        nameArr.add("name3");

        abilityArr.add("ability0");
        abilityArr.add("ability1");
        abilityArr.add("ability2");
        abilityArr.add("ability3");
    }

    private void modifyHealth(int mod)
    {
        if((mod > 0 && stats[StatTypes.HEALTH.index].val < startingHealth) ||
            (mod < 0 && stats[StatTypes.HEALTH.index].val > 0)) {
            stats[StatTypes.HEALTH.index].val += mod;
            stats[StatTypes.HEALTH.index].textView.setText(Integer.toString(stats[StatTypes.HEALTH.index].val));
        }
    }

    private void genStatValues()
    {
        int total = 46;
        int statMin = 13;
        int statMax = 18;
        int diff = 0;
        int inc = 0;
        Random rand = new Random();

        // Generating the stat values
        for(int i = 0; i < 3; ++i)
        {
            stats[i].val = rand.nextInt(statMax - statMin + 1) + statMax;
            diff += stats[i].val;
        }

        // Finding the difference between the actual and desired total
        diff -= total;

        // Checking if we can return
        if(diff == 0)
            return;

        if(diff > 0)
            inc = -1;
        else if (diff < 0)
            inc = 1;


        // Balancing out the stats
        while(diff != 0)
        {
            int index = rand.nextInt(3);

            if(stats[index].val < statMax && inc > 0)
            {
                stats[index].val += inc;
                diff += inc;
            }
            else if(stats[index].val > statMin && inc < 0)
            {
                stats[index].val += inc;
                diff += inc;
            }
        }
    }

    private class Stat
    {
        public int val;
        public TextView textView;
        public ImageButton buttons[] = new ImageButton[2];

        public void update(View v)
        {
            if(v == buttons[ButtonType.INCREMENT.index])
                val++;
            else if (v == buttons[ButtonType.DECREMENT.index])
                val--;

            textView.setText(Integer.toString(val));
        }
    }
}

