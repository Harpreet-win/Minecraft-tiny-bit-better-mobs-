package com.example.aidrivenmobs;

import java.util.HashSet;
import java.util.Set;

public class MobPersonality {
    private String mobType;
    private Set<String> personalityTags;

    public MobPersonality(String mobType) {
        this.mobType = mobType;
        this.personalityTags = new HashSet<>();
        // Initialize with default personality traits
        initializeDefaultPersonality();
    }

    private void initializeDefaultPersonality() {
        // Base personality traits based on mob type
        switch (mobType.toLowerCase()) {
            case "creeper":
                personalityTags.add("ambusher");
                personalityTags.add("coward");
                break;
            case "skeleton":
                personalityTags.add("brave");
                personalityTags.add("squad_member");
                break;
            case "zombie":
                personalityTags.add("relentless");
                personalityTags.add("pack_hunter");
                break;
            default:
                personalityTags.add("neutral");
                break;
        }
    }

    public boolean hasPersonalityTag(String tag) {
        return personalityTags.contains(tag);
    }

    public void addPersonalityTag(String tag) {
        personalityTags.add(tag);
    }

    public void removePersonalityTag(String tag) {
        personalityTags.remove(tag);
    }

    public Set<String> getPersonalityTags() {
        return new HashSet<>(personalityTags);
    }

    public String getMobType() {
        return mobType;
    }
}