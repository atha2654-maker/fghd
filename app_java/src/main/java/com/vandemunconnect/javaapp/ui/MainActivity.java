package com.vandemunconnect.javaapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vandemunconnect.javaapp.R;
import com.vandemunconnect.javaapp.databinding.ActivityMainBinding;
import com.vandemunconnect.javaapp.model.SimpleItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final List<SimpleItem> chatItems = new ArrayList<>();
    private final List<SimpleItem> eventItems = new ArrayList<>();
    private final List<SimpleItem> plannerItems = new ArrayList<>();

    private final SimpleItemAdapter chatAdapter = new SimpleItemAdapter();
    private final SimpleItemAdapter eventsAdapter = new SimpleItemAdapter();
    private final SimpleItemAdapter plannerAdapter = new SimpleItemAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        seedData();
        setupLogin();
        setupAdapters();
        setupButtons();
        setupBottomNav();
    }

    private void seedData() {
        chatItems.add(new SimpleItem("chair", "Welcome delegates. Session starts at 10:00 AM."));
        eventItems.add(new SimpleItem("VandeMUN Winter Summit", "New Delhi • 2026-01-18 • ₹2500"));
        eventItems.add(new SimpleItem("National Diplomacy Challenge (Past)", "Best Delegate - India Council"));
        plannerItems.add(new SimpleItem("Position paper submission", "Upload before midnight"));
    }

    private void setupLogin() {
        binding.btnSendCode.setOnClickListener(v -> {
            String phone = textOf(binding.etPhone);
            String email = textOf(binding.etEmail);
            if (phone.isEmpty() && email.isEmpty()) {
                toast("Enter phone or email");
                return;
            }
            binding.loginContainer.setVisibility(View.GONE);
            binding.verifyContainer.setVisibility(View.VISIBLE);
        });

        binding.btnVerify.setOnClickListener(v -> {
            String code = textOf(binding.etCode);
            if (code.length() < 4) {
                toast("Enter valid verification code");
                return;
            }
            binding.verifyContainer.setVisibility(View.GONE);
            binding.dashboardContainer.setVisibility(View.VISIBLE);
            showTab(R.id.menu_home);
        });
    }

    private void setupAdapters() {
        binding.rvChat.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChat.setAdapter(chatAdapter);
        chatAdapter.submit(chatItems);

        binding.rvEvents.setLayoutManager(new LinearLayoutManager(this));
        binding.rvEvents.setAdapter(eventsAdapter);
        eventsAdapter.submit(eventItems);

        binding.rvPlanner.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPlanner.setAdapter(plannerAdapter);
        plannerAdapter.submit(plannerItems);
    }

    private void setupButtons() {
        binding.btnChatSend.setOnClickListener(v -> {
            String message = textOf(binding.etChatInput);
            if (message.isEmpty()) return;
            chatItems.add(new SimpleItem("you", message));
            chatAdapter.submit(chatItems);
            binding.etChatInput.setText("");
        });

        binding.btnAddEvent.setOnClickListener(v -> {
            String name = textOf(binding.etEventName);
            if (name.isEmpty()) {
                toast("Event name required");
                return;
            }
            String meta = textOf(binding.etEventLocation) + " • " + textOf(binding.etEventDate) + " • " + textOf(binding.etEventFee);
            eventItems.add(new SimpleItem(name, meta));
            eventsAdapter.submit(eventItems);
            binding.etEventName.setText("");
            binding.etEventLocation.setText("");
            binding.etEventDate.setText("");
            binding.etEventFee.setText("");
            binding.etEventLink.setText("");
        });

        binding.btnAddReminder.setOnClickListener(v -> {
            String title = textOf(binding.etReminderTitle);
            if (title.isEmpty()) {
                toast("Reminder title required");
                return;
            }
            plannerItems.add(new SimpleItem(title, textOf(binding.etReminderNote)));
            plannerAdapter.submit(plannerItems);
            binding.etReminderTitle.setText("");
            binding.etReminderNote.setText("");
        });

        binding.btnSaveProfile.setOnClickListener(v -> toast("Profile saved"));
        binding.btnVoiceCall.setOnClickListener(v -> toast("Voice call feature scaffold"));
        binding.btnVideoCall.setOnClickListener(v -> toast("Video call feature scaffold"));
    }

    private void setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            showTab(item.getItemId());
            return true;
        });
    }

    private void showTab(int tabId) {
        binding.tabHome.setVisibility(tabId == R.id.menu_home ? View.VISIBLE : View.GONE);
        binding.tabChat.setVisibility(tabId == R.id.menu_chat ? View.VISIBLE : View.GONE);
        binding.tabEvents.setVisibility(tabId == R.id.menu_events ? View.VISIBLE : View.GONE);
        binding.tabPlanner.setVisibility(tabId == R.id.menu_planner ? View.VISIBLE : View.GONE);
        binding.tabProfile.setVisibility(tabId == R.id.menu_profile ? View.VISIBLE : View.GONE);
    }

    private String textOf(android.widget.EditText editText) {
        return editText.getText().toString().trim();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
