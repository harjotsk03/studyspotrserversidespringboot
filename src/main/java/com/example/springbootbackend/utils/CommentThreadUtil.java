package com.example.springbootbackend.utils;

import com.example.springbootbackend.models.Comment;
import java.util.*;

public class CommentThreadUtil {

    public static List<CommentNode> buildThreadTree(List<Comment> allComments) {
        Map<String, CommentNode> idToNode = new HashMap<>();
        List<CommentNode> rootComments = new ArrayList<>();

        // Convert comments to nodes
        for (Comment comment : allComments) {
            idToNode.put(comment.getId(), new CommentNode(comment));
        }

        // Build the tree
        for (Comment comment : allComments) {
            String parentId = comment.getParentId();
            CommentNode currentNode = idToNode.get(comment.getId());
            if (parentId == null) {
                rootComments.add(currentNode);
            } else {
                CommentNode parentNode = idToNode.get(parentId);
                if (parentNode != null) {
                    parentNode.addReply(currentNode);
                } else {
                    rootComments.add(currentNode);
                }
            }
        }

        return rootComments;
    }

    public static class CommentNode {
        private String id;
        private String content;
        private String userId;
        private Date createdAt;
        private List<String> likes;
        private List<String> dislikes;
        private List<CommentNode> replies = new ArrayList<>();

        public CommentNode(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.userId = comment.getUserId();
            this.createdAt = comment.getCreatedAt();
            this.likes = comment.getLikes();
            this.dislikes = comment.getDislikes();
        }

        public void addReply(CommentNode reply) {
            this.replies.add(reply);
        }

        public String getId() { return id; }
        public String getContent() { return content; }
        public String getUserId() { return userId; }
        public Date getCreatedAt() { return createdAt; }
        public List<String> getLikes() { return likes; }
        public List<String> getDislikes() { return dislikes; }
        public List<CommentNode> getReplies() { return replies; }
    }
} 
