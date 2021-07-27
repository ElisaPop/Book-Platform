<template>
    <div>
        <v-container>
        <v-tabs v-model="tab" background-color="#f4f1ea" max-width="800" centered>
            <v-text-field v-model="searchText" label="Search" >
            </v-text-field>
            <v-tabs-slider></v-tabs-slider>
            <v-btn color="#3c2819" icon @click="search">
            <v-icon>mdi-magnify </v-icon>
            </v-btn>
            <v-btn color="#3c2819" icon onclick="window.location.href = 'http://localhost:8081/books/-1';">
            <v-icon>mdi-plus-circle </v-icon>
            </v-btn>

            <v-btn color="#f4f1ea" >
                Books
            </v-btn>
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Tags' })" >
                Tags
            </v-btn>
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Users' })">
                Users
            </v-btn>
        
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
            <v-row no-gutters class="mt-4" cols="1" sm="4" v-for="book in books" :key="book.id">
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
    name: "Books",
    components: {
        BookCard
    },
    data () {
        return {
            searchText: '',
            tab: 0,
            books:[],

        }
    },
    created(){
        
            this.$http.get("http://localhost:8080/book").then( result => {
            this.books = result.data;
        })
    },
    methods: {
        search(){
            if(this.searchText !== ''){
                this.$http.get("http://localhost:8080/book/search?title="+ this.searchText).then( result => {
                this.books = result.data;
                })
            }
        }
    }
}
</script>