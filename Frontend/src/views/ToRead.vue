<template>
    <div>
        <v-container>
        <v-tabs v-model="tab" right background-color="#f4f1ea" max-width="800" >
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Ratings' })">
                Ratings
            </v-btn>
            <v-btn color="#f4f1ea">
                To Read
            </v-btn>
            <v-btn color="#3c2819" v-if="this.$store.getters.userRole == 2" dark @click ="$router.push({ name: 'Written' })">
                Written
            </v-btn>
        
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
            <v-row no-gutters class="mt-4" cols="1" sm="4" v-for="book in bookToRead" :key="book.id">
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
    name: "ToRead",
    components: {
        BookCard
    },
    data () {
        return {
            searchRate: 0,
            tab: 0,
            bookToRead:[],
        }
    },
    created(){
        this.$http.get("http://localhost:8080/toread?id="+ this.$store.getters.userData.userId+ 
                "&rating=0").then( result => {
            this.bookToRead = result.data;
            console.log(this.bookToRead)
        })
        
    }
}
</script>