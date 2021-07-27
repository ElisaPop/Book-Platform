<template>
  <div class="home">
    <h1 v-if="this.$store.getters.userRole !== 0">
        Hello, {{this.$store.getters.userData.name}}
    </h1>
    <v-container>
        <v-tabs v-model="tab" background-color="#f4f1ea" max-width="800" centered>
          <h2> Top picks: </h2>
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
             <v-row no-gutters class="mt-4">
            <v-col cols="11" sm="3" v-for="book in booksTop" :key="book.id">
                <BookCard :bookParameter="book" />
            </v-col>
            </v-row>
        </v-tab-item>
      </v-tabs-items>
        <v-tabs v-model="tab" background-color="#f4f1ea" max-width="800" centered>
          <h2> New books: </h2>
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
             <v-row no-gutters class="mt-4">
            <v-col cols="11" sm="3" v-for="book in booksRecent" :key="book.id">
                <BookCard :bookParameter="book" />
            </v-col>
            </v-row>
        </v-tab-item>
        </v-tabs-items>
        </v-container>
  </div>
</template>

<script>
import BookCard from "../components/BookCard";
export default {
  name: 'Home',
  components: {
        BookCard
    },
  data () {
        return {
          tab: 0,
          booksTop:[],
          booksRecent:[],

        }
    },
  created(){
        this.$http.get("http://localhost:8080/book/top").then( result => {
            this.booksTop = result.data;
        }),
        console.log("sasa")
        this.$http.get("http://localhost:8080/book/new").then( result => {
            this.booksRecent = result.data;
        })
    },
}
</script>
