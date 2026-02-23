package com.vandemunconnect.javaapp.ui;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int TAB_HOME = 1;
    private static final int TAB_CHAT = 2;
    private static final int TAB_EVENTS = 3;
    private static final int TAB_PLANNER = 4;
    private static final int TAB_PROFILE = 5;

    private FrameLayout root;
    private LinearLayout loginScreen;
    private LinearLayout verifyScreen;
    private LinearLayout dashboardScreen;

    private View tabHome;
    private View tabChat;
    private View tabEvents;
    private View tabPlanner;
    private View tabProfile;

    private EditText etPhone;
    private EditText etEmail;
    private EditText etCode;
    private EditText etChat;
    private EditText etEventName;
    private EditText etEventLocation;
    private EditText etEventDate;
    private EditText etEventFee;
    private EditText etReminderTitle;
    private EditText etReminderNote;

    private final List<RowItem> chatItems = new ArrayList<>();
    private final List<RowItem> eventItems = new ArrayList<>();
    private final List<RowItem> plannerItems = new ArrayList<>();

    private SimpleAdapter chatAdapter;
    private SimpleAdapter eventsAdapter;
    private SimpleAdapter plannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seedData();
        buildUi();
        setContentView(root);
    }

    private void seedData() {
        chatItems.add(new RowItem("Chair", "Welcome delegates. Public room is active."));
        eventItems.add(new RowItem("VandeMUN Summit", "New Delhi • 2026-01-18 • ₹2500"));
        plannerItems.add(new RowItem("Position Paper", "Submit before 11:59 PM"));
    }

    private void buildUi() {
        root = new FrameLayout(this);
        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        loginScreen = buildLoginScreen();
        verifyScreen = buildVerifyScreen();
        dashboardScreen = buildDashboardScreen();

        verifyScreen.setVisibility(View.GONE);
        dashboardScreen.setVisibility(View.GONE);

        root.addView(loginScreen);
        root.addView(verifyScreen);
        root.addView(dashboardScreen);
    }

    private LinearLayout buildLoginScreen() {
        LinearLayout screen = verticalContainer();
        screen.setPadding(dp(16), dp(16), dp(16), dp(16));

        screen.addView(title("Vande MUN Connect"));
        screen.addView(textView("Java-only single-file flow for Android Studio."));

        etPhone = input("Phone number", InputType.TYPE_CLASS_PHONE);
        etEmail = input("Email", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        Button send = button("Send OTP / Email Code");
        send.setOnClickListener(v -> {
            if (text(etPhone).isEmpty() && text(etEmail).isEmpty()) {
                toast("Enter phone or email");
                return;
            }
            loginScreen.setVisibility(View.GONE);
            verifyScreen.setVisibility(View.VISIBLE);
        });

        screen.addView(etPhone);
        screen.addView(etEmail);
        screen.addView(send);
        return screen;
    }

    private LinearLayout buildVerifyScreen() {
        LinearLayout screen = verticalContainer();
        screen.setPadding(dp(16), dp(16), dp(16), dp(16));

        screen.addView(title("Direct Code Verification"));
        etCode = input("Enter 4+ digit code", InputType.TYPE_CLASS_NUMBER);

        Button verify = button("Verify & Open Dashboard");
        verify.setOnClickListener(v -> {
            if (text(etCode).length() < 4) {
                toast("Invalid verification code");
                return;
            }
            verifyScreen.setVisibility(View.GONE);
            dashboardScreen.setVisibility(View.VISIBLE);
            showTab(TAB_HOME);
        });

        screen.addView(etCode);
        screen.addView(verify);
        return screen;
    }

    private LinearLayout buildDashboardScreen() {
        LinearLayout screen = verticalContainer();

        FrameLayout tabContainer = new FrameLayout(this);
        tabContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));

        tabHome = buildHomeTab();
        tabChat = buildChatTab();
        tabEvents = buildEventsTab();
        tabPlanner = buildPlannerTab();
        tabProfile = buildProfileTab();

        tabContainer.addView(tabHome);
        tabContainer.addView(tabChat);
        tabContainer.addView(tabEvents);
        tabContainer.addView(tabPlanner);
        tabContainer.addView(tabProfile);

        BottomNavigationView bottomNavigation = new BottomNavigationView(this);
        bottomNavigation.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        Menu menu = bottomNavigation.getMenu();
        menu.add(Menu.NONE, TAB_HOME, Menu.NONE, "Home");
        menu.add(Menu.NONE, TAB_CHAT, Menu.NONE, "Chat");
        menu.add(Menu.NONE, TAB_EVENTS, Menu.NONE, "Events");
        menu.add(Menu.NONE, TAB_PLANNER, Menu.NONE, "Planner");
        menu.add(Menu.NONE, TAB_PROFILE, Menu.NONE, "Profile");

        bottomNavigation.setOnItemSelectedListener(item -> {
            showTab(item.getItemId());
            return true;
        });

        screen.addView(tabContainer);
        screen.addView(bottomNavigation);
        return screen;
    }

    private View buildHomeTab() {
        LinearLayout tab = verticalContainer();
        tab.setPadding(dp(16), dp(16), dp(16), dp(16));
        tab.addView(title("Home"));
        tab.addView(textView("Public chat room, conference, voice/video call, whiteboard and screen-share scaffolds are ready."));
        return tab;
    }

    private View buildChatTab() {
        LinearLayout tab = verticalContainer();
        tab.setPadding(dp(12), dp(12), dp(12), dp(12));

        LinearLayout callBar = new LinearLayout(this);
        callBar.setOrientation(LinearLayout.HORIZONTAL);
        callBar.setGravity(Gravity.END);

        Button call = buttonInline("Call");
        Button voice = buttonInline("Voice");
        Button video = buttonInline("Video");
        call.setOnClickListener(v -> toast("Call scaffold"));
        voice.setOnClickListener(v -> toast("Voice call scaffold"));
        video.setOnClickListener(v -> toast("Video call scaffold"));

        callBar.addView(call);
        callBar.addView(voice);
        callBar.addView(video);

        RecyclerView list = recycler();
        chatAdapter = new SimpleAdapter();
        list.setAdapter(chatAdapter);
        chatAdapter.submit(chatItems);

        etChat = input("Text / Voice note placeholder", InputType.TYPE_CLASS_TEXT);
        Button send = button("Send Message");
        send.setOnClickListener(v -> {
            String msg = text(etChat);
            if (msg.isEmpty()) {
                return;
            }
            chatItems.add(new RowItem("You", msg));
            chatAdapter.submit(chatItems);
            etChat.setText("");
        });

        tab.addView(callBar);
        tab.addView(list);
        tab.addView(etChat);
        tab.addView(send);
        return tab;
    }

    private View buildEventsTab() {
        LinearLayout tab = verticalContainer();
        tab.setPadding(dp(12), dp(12), dp(12), dp(12));

        etEventName = input("Event name", InputType.TYPE_CLASS_TEXT);
        etEventLocation = input("Location", InputType.TYPE_CLASS_TEXT);
        etEventDate = input("Date", InputType.TYPE_CLASS_DATETIME);
        etEventFee = input("Fee", InputType.TYPE_CLASS_TEXT);

        Button add = button("Add Event");
        add.setOnClickListener(v -> {
            String name = text(etEventName);
            if (name.isEmpty()) {
                toast("Event name required");
                return;
            }
            String details = text(etEventLocation) + " • " + text(etEventDate) + " • " + text(etEventFee);
            eventItems.add(new RowItem(name, details));
            eventsAdapter.submit(eventItems);
            etEventName.setText("");
            etEventLocation.setText("");
            etEventDate.setText("");
            etEventFee.setText("");
        });

        RecyclerView list = recycler();
        eventsAdapter = new SimpleAdapter();
        list.setAdapter(eventsAdapter);
        eventsAdapter.submit(eventItems);

        tab.addView(etEventName);
        tab.addView(etEventLocation);
        tab.addView(etEventDate);
        tab.addView(etEventFee);
        tab.addView(add);
        tab.addView(list);
        return tab;
    }

    private View buildPlannerTab() {
        LinearLayout tab = verticalContainer();
        tab.setPadding(dp(12), dp(12), dp(12), dp(12));

        etReminderTitle = input("Reminder title", InputType.TYPE_CLASS_TEXT);
        etReminderNote = input("Reminder note", InputType.TYPE_CLASS_TEXT);

        Button add = button("Add Reminder");
        add.setOnClickListener(v -> {
            String title = text(etReminderTitle);
            if (title.isEmpty()) {
                toast("Reminder title required");
                return;
            }
            plannerItems.add(new RowItem(title, text(etReminderNote)));
            plannerAdapter.submit(plannerItems);
            etReminderTitle.setText("");
            etReminderNote.setText("");
        });

        RecyclerView list = recycler();
        plannerAdapter = new SimpleAdapter();
        list.setAdapter(plannerAdapter);
        plannerAdapter.submit(plannerItems);

        tab.addView(etReminderTitle);
        tab.addView(etReminderNote);
        tab.addView(add);
        tab.addView(list);
        return tab;
    }

    private View buildProfileTab() {
        LinearLayout tab = verticalContainer();
        tab.setPadding(dp(12), dp(12), dp(12), dp(12));

        tab.addView(input("Name", InputType.TYPE_CLASS_TEXT));
        tab.addView(input("Portfolio", InputType.TYPE_CLASS_TEXT));
        tab.addView(input("Committee", InputType.TYPE_CLASS_TEXT));
        tab.addView(input("Contact", InputType.TYPE_CLASS_PHONE));
        tab.addView(input("Bio", InputType.TYPE_CLASS_TEXT));
        tab.addView(input("WhatsApp delegation", InputType.TYPE_CLASS_PHONE));

        Switch adminBadge = new Switch(this);
        adminBadge.setText("Admin badge");
        Switch ownerBadge = new Switch(this);
        ownerBadge.setText("Owner badge");

        Button save = button("Save Profile");
        save.setOnClickListener(v -> toast("Profile saved"));

        tab.addView(adminBadge);
        tab.addView(ownerBadge);
        tab.addView(save);
        return tab;
    }

    private void showTab(int tabId) {
        tabHome.setVisibility(tabId == TAB_HOME ? View.VISIBLE : View.GONE);
        tabChat.setVisibility(tabId == TAB_CHAT ? View.VISIBLE : View.GONE);
        tabEvents.setVisibility(tabId == TAB_EVENTS ? View.VISIBLE : View.GONE);
        tabPlanner.setVisibility(tabId == TAB_PLANNER ? View.VISIBLE : View.GONE);
        tabProfile.setVisibility(tabId == TAB_PROFILE ? View.VISIBLE : View.GONE);
    }

    private RecyclerView recycler() {
        RecyclerView rv = new RecyclerView(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));
        return rv;
    }

    private LinearLayout verticalContainer() {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return ll;
    }

    private TextView title(String value) {
        TextView tv = new TextView(this);
        tv.setText(value);
        tv.setTextSize(22f);
        tv.setPadding(0, 0, 0, dp(12));
        return tv;
    }

    private TextView textView(String value) {
        TextView tv = new TextView(this);
        tv.setText(value);
        tv.setTextSize(16f);
        tv.setPadding(0, 0, 0, dp(12));
        return tv;
    }

    private EditText input(String hint, int inputType) {
        EditText et = new EditText(this);
        et.setHint(hint);
        et.setInputType(inputType);
        et.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return et;
    }

    private Button button(String label) {
        Button button = new Button(this);
        button.setText(label);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return button;
    }

    private Button buttonInline(String label) {
        Button button = new Button(this);
        button.setText(label);
        button.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        return button;
    }

    private String text(EditText editText) {
        return editText.getText().toString().trim();
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private static class RowItem {
        final String title;
        final String subtitle;

        RowItem(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.RowVH> {
        private final List<RowItem> data = new ArrayList<>();

        void submit(List<RowItem> items) {
            data.clear();
            data.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public RowVH onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout row = new LinearLayout(parent.getContext());
            row.setOrientation(LinearLayout.VERTICAL);
            row.setPadding(24, 20, 24, 20);

            TextView title = new TextView(parent.getContext());
            title.setTextSize(16f);
            TextView subtitle = new TextView(parent.getContext());
            subtitle.setTextSize(14f);

            row.addView(title);
            row.addView(subtitle);
            return new RowVH(row, title, subtitle);
        }

        @Override
        public void onBindViewHolder(RowVH holder, int position) {
            RowItem item = data.get(position);
            holder.title.setText(item.title);
            holder.subtitle.setText(item.subtitle);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        static class RowVH extends RecyclerView.ViewHolder {
            final TextView title;
            final TextView subtitle;

            RowVH(View itemView, TextView title, TextView subtitle) {
                super(itemView);
                this.title = title;
                this.subtitle = subtitle;
            }
        }
    }
}
