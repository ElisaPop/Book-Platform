<template>
    <div>
        <v-container>
        <v-tabs v-model="tab" background-color="#f4f1ea" max-width="800" centered>
            <v-text-field v-model="searchRate" label="Search" >
            </v-text-field>
            <v-tabs-slider></v-tabs-slider>
            <v-btn color="#3c2819" icon @click="search">
            <v-icon>mdi-magnify </v-icon>
            </v-btn>

            <v-btn color="#f4f1ea" >
                Ratings
            </v-btn>
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'ToRead' })" >
                To Read
            </v-btn>
            <v-btn color="#3c2819"  v-if="this.$store.getters.userRole == 2" dark @click ="$router.push({ name: 'Written' })">
                Written
            </v-btn>
        
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
            <v-row no-gutters class="mt-4" cols="1" sm="4" v-for="book in bookRatings" :key="book.id">
                <BookCard :bookParameter="book" />
            </v-row>
        </v-tab-item>
        </v-tabs-items>
        </v-container>

    </div>
</template>

<script>
import BookCard from "../components/BookCard";
export default {
    name: "Ratings",
    components: {
        BookCard
    },
    data () {
        return {
            searchRate: 0,
            tab: 0,
            bookRatings:[],
        }
    },
    created(){
        this.$http.get("http://localhost:8080/rating?id="+ this.$store.getters.userData.userId+ 
                "&rating=0").then( result => {
            this.bookRatings = result.data;
            console.log(this.bookRatings)
        })
        
    },
    methods: {
        search(){
            if(this.searchRate !== 0){
                this.$http.get("http://localhost:8080/rating/search?id=" + this.$store.getters.userData.userId + 
                "&rating="+ this.searchRate).then( result => {
                this.bookRatings = result.data;
                })
            }
        }
    }
}
</script>