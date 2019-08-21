package com.example.simple.simplethink.model;

import java.util.List;
import java.util.Map;

/**
 * Created by mobileteam on 2019/8/20.
 */

public class PracticesResponse {
        public String unique_id;
        public String date_time;
        public Course course;
        public Section section;

        public String getUnique_id() {
            return unique_id;
        }

        public String getDate_time() {
            return date_time;
        }

        public Course getCourse() {
            return course;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public void setSection(Section section) {
            this.section = section;
        }

        public Section getSection() {

            return section;
        }

    public static class Course {
        public int id;
        public String title;
        public String subtitle;
        public String content;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Section {
        public int id;
        public String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
