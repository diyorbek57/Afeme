package com.ayizor.afeme.model

class GalleryDetails {
    var isImage: Boolean = false
    var post: Post? = null
    var stories: ArrayList<Gallery> = ArrayList<Gallery>()

    constructor() {
        this.isImage = true
    }

    constructor(post: Post) {
        this.post = post
        this.isImage = false
    }

    constructor(stories: ArrayList<Gallery>) {
        this.stories = stories
        this.isImage = false
    }
}