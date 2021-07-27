<template>
    <div>
        <v-container>
        <v-tabs v-model="tab" background-color="#f4f1ea" max-width="800">
            <v-spacer> </v-spacer>
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Ratings' })">
                Ratings
            </v-btn>
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'ToRead' })" >
                To Read
            </v-btn>
            <v-btn color="#f4f1ea"  v-if="this.$store.getters.userRole == 2">
                Written
            </v-btn>
        
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
            <v-row no-gutters class="mt-4" cols="1" sm="4" v-for="book in bookWritten" :key="book.id">
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
    name: "Written",
    components: {
        BookCard
    },
    data () {
        return {
            searchRate: 0,
            tab: 0,
            bookWritten:[],
        }
    },
    created(){
        this.$http.get("http://localhost:8080/book/written?id="+ this.$store.getters.userData.userId).then( result => {
            this.bookWritten = result.data;
            console.log(this.bookWritten)
        })
        
    }
}
</script>