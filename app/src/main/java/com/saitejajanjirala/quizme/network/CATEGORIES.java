package com.saitejajanjirala.quizme.network;

import com.saitejajanjirala.quizme.R;

public enum CATEGORIES {
    LINUX("Linux", R.drawable.linux),
    BASH("BASH",R.drawable.bash),
    PHP("Php",R.drawable.php),
    Docker("Docker",R.drawable.docker),
    HTML("HTML",R.drawable.html),
    MYSQL("MySQL",R.drawable.mysql),
    WORDPRESS("WordPress",R.drawable.wordpress),
    LARAVEL("Laravel",R.drawable.laravel),
    KUBERNETES("Kubernetes",R.drawable.kubernetes),
    JAVASCRIPT("JavaScript",R.drawable.javascript),
    DEVOPS("DevOps",R.drawable.devops),
    RANDOM("Random",R.drawable.random)
    ;

    private String name;
    private int resId;
    CATEGORIES(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }
}
