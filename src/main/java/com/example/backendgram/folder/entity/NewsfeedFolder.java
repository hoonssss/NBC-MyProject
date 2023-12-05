package com.example.backendgram.folder.entity;

import com.example.backendgram.folder.entity.Folder;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "newsfeed_folder")
public class NewsfeedFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsfeed_id", nullable = false)
    private NewsFeed newsFeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "newsfeedFolder_id")
    private NewsFeed newsfeedFolder;

    public NewsfeedFolder(NewsFeed newsFeed, Folder folder) {
        this.newsFeed = newsFeed;
        this.folder = folder;
    }
}