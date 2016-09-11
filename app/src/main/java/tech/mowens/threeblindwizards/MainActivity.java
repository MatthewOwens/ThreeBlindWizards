package tech.mowens.threeblindwizards;

import android.graphics.drawable.Drawable;
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

        // Setting the wizard image
        int wizNum = rand.nextInt(8) + 1;
        String drawName = "wiz" + Integer.toString(wizNum);
        wizNum = getResources().getIdentifier(drawName, "drawable", getPackageName());
        sprite.setImageResource(wizNum);
    }

    private void populateStrings()
    {
        nameArr.add("Falkor");
        nameArr.add("Azagoth");
        nameArr.add("Nividya");
        nameArr.add("Auriel");
        nameArr.add("Harion");
        nameArr.add("Ulstiof");
        nameArr.add("Yellama");
        nameArr.add("Sarria");
        nameArr.add("Perkara");
        nameArr.add("Sukamu");
        nameArr.add("Erado");
        nameArr.add("Renloky");
        nameArr.add("Purkoi");
        nameArr.add("Erivlad");
        nameArr.add("Zeldari");
        nameArr.add("Stargaat");
        nameArr.add("Luciyef");
        nameArr.add("Crabbles");
        nameArr.add("The Bearded One");
        nameArr.add("Pixel Wizard");
        nameArr.add("Dog");

        abilityArr.add("Tough Skin\n\nBegin with 7 extra health");
        abilityArr.add("First to Betray\n\nYou take the first turn and your starting hand is 3");
        abilityArr.add("Many Leather-Bound Books\n\nYou may begin the game with two extra cards of your choice in the deck, from the line-up");
        abilityArr.add("Glass Cannon\n\nYour highest starting stat gains a bonus of +5, your lowest starting stat gains -5. You may choose if there's a tie");
        abilityArr.add("Extra Lessions\n\nBegin with 3 extra points to distribute as you choose");
        abilityArr.add("Spell Shield\n\nOnce, you may ignore all damage you take in a turn");
        abilityArr.add("Magic Cocktail\n\nOnce, you may cast or discard any cards as if they were any type");
        abilityArr.add("Agitator\n\nTwice, you may prevent a player from drawing a card at the end of their turn");
        abilityArr.add("Bomb Shelter Spell\n\nBombs only deal 4 damage to all players. Must be announced upon the first bomb cast");
        abilityArr.add("Channeling\n\nOnce, you may draw two additional cards at the end of the turn");
        abilityArr.add("Bad Magic\n\nOnce, you may target a player and have them lose 3 points of a stat of your choice");
        abilityArr.add("Yodeller\n\nOnce, you may chant twice in a turn");
        abilityArr.add("Anarchist\n\nOnce, you may Bomb by discarding instead of casting");
        abilityArr.add("Full Force\n\nOnce when you attack, you have a bonus +4 to the attack");
        abilityArr.add("Final Form\n\nYour starting health is reduced by 4. When you are defeated, you survive with 1 Health and gain +5 to all stats");
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

