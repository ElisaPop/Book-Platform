<template>
  <v-card class="mx-auto mb-3" max-width="800" outlined shaped>
    <v-list-item three-line>
      <v-list-item-content>
        <div class="overline mb-4">{{ bookParameter.bookId }}</div>
        
        <v-list-item-title class="display-1 mb-6">
            <v-list-item-avatar size="80">
                <img :src="bookParameter.photo" />
            </v-list-item-avatar>
            {{ bookParameter.title }}
        </v-list-item-title>
        <v-list-item-subtitle class="mb-3 mt-2">
          <span class="font-weight-bold">Author:</span>
          {{ bookParameter.authors }}
        </v-list-item-subtitle>
        <v-list-item-subtitle class="mb-2 mt-2">
          <span class="font-weight-bold">Year published:</span>
          {{ bookParameter.publicationYear }}
        </v-list-item-subtitle>
        <v-list-item-subtitle class="mb-2 mt-2">
          <span class="font-weight-bold">Average rating:</span>
          {{ bookParameter.averageRating }}
        </v-list-item-subtitle>
        <v-list-item-subtitle class="mb-2 mt-2">
          <span class="font-weight-bold">Rating count:</span>
          {{ bookParameter.ratingsCount }}
        </v-list-item-subtitle>
      </v-list-item-content>
        
    </v-list-item>
    <v-rating 
        v-model="rating"
        :half-increments=true
        :readonly=true
        background-color="orange lighten-3"
        color="orange"
        medium>
    </v-rating>
    <v-card-actions v-if="this.$store.getters.userData!= null">
        <router-link :to="`books/${bookParameter.bookId}`">
        <v-btn 
          v-if="this.$store.getters.userRole == 3 || this.$store.getters.userData.userId == bookParameter.authorId"
        color="#f4f1ea">
            Edit
        </v-btn>
      </router-link>
       <v-dialog v-model="dialog" persistent max-width="600px">
        <template v-slot:activator="{ on }">
          <v-btn color="#f4f1ea" v-on="on">Add Rating</v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">Your rating:</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-rating 
                v-model="newRating"
                background-color="orange lighten-3"
                color="orange"
                medium>
              </v-rating>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
            <v-btn color="blue darken-1" text @click="addRating()">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-btn 
        v-if="this.$store.getters.userData!= null"
        color="#f4f1ea"
        @click="addToRead()">
            Want to read
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
    name: "BookCard",
    data() {
        return {
            dialog:false,
            rating: this.bookParameter.averageRating,
            newRating: this.bookParameter.rating,
            ratingObj:{
              bookId: this.bookParameter.bookId,
              userId: -1,
              rating: this.bookParameter.rating
            },
            toReadObj:{
              userId: -1,
              bookId: this.bookParameter.bookId
            },
            existent: false
        }
    },
    props: {
      bookParameter: {
          type: Object,
          required: true
      },
    },
    // watch:{
    //   rating: function(){
    //       console.log("took click")
    //       if(this.bookParameter.rating == 0){
    //           this.bookParameter.rating = this.rating;
    //           this.ratingObj.bookId = this.bookParameter.bookId,
    //           this.ratingObj.userId= this.$store.getters.userData.userId,
    //           this.ratingObj.rating= this.bookParameter.rating
    //           this.$http.post("http://localhost:8080/rating", this.ratingObj ).then( result => {
    //             Object.assign(this.ratingObj, result.data);
    //             console.log(this.ratingObj)
    //         })
    //       }
    //       else{
    //         this.bookParameter.rating = this.rating;
    //           this.ratingObj.bookId = this.bookParameter.bookId,
    //           this.ratingObj.userId= this.$store.getters.userData.userId,
    //           this.ratingObj.rating= this.bookParameter.rating
    //           this.$http.put("http://localhost:8080/rating", this.ratingObj).then( result => {
    //           this.ratingObj = result.data;
    //           console.log(this.ratingObj)
    //         })
    //       } 
    //     }
    // },
    methods: {
        addRating() {
            this.dialog = false
            console.log("took click")
            this.ratingObj.userId= this.$store.getters.userData.userId
            this.toReadObj.userId= this.$store.getters.userData.userId
            this.ratingObj.rating = this.newRating
            
            this.$http.delete("http://localhost:8080/toread?bookid="+ this.toReadObj.bookId + 
            "&userid=" + this.toReadObj.userId).then( result => {
              Object.assign(this.toReadObj, result.data);
              console.log(this.toReadObj)
            }),
            this.$http.post("http://localhost:8080/rating", this.ratingObj).then( result => {
              Object.assign(this.ratingObj, result.data);
              console.log(this.ratingObj)
              
            })
        },
        addToRead() {
            console.log("took click")
            this.toReadObj.bookId = this.bookParameter.bookId,
            this.toReadObj.userId= this.$store.getters.userData.userId
            this.$http.post("http://localhost:8080/toread", this.toReadObj ).then( result => {
              Object.assign(this.toReadObj, result.data);
              console.log(this.toReadObj)
            })
        }
    }
}
</script>