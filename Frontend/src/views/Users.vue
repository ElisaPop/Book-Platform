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

            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Books' })"  >
                Books
            </v-btn>
             <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Tags' })" >
                Tags
            </v-btn>
            <v-btn color="#f4f1ea" >
                Users
            </v-btn>
        
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
            <v-row no-gutters class="mt-4">
            <v-col cols="1" sm="4" v-for="user in users" :key="user.userId">
                <UserCard :userParameter="user" />
            </v-col>
            </v-row>
        </v-tab-item>
        </v-tabs-items>
        </v-container>

    </div>
</template>


<script>
import UserCard from "../components/UserCard";
export default {
    name: "Users",
    components: {
        UserCard
    },
    data () {
        return {
            searchText: '',
            tab:0,
            users:[]
        }
    },
   created(){
        this.$http.get("http://localhost:8080/users").then( result => {
            this.users = result.data;
        })
    },
    methods: {
        search(){
            if(this.searchText !== ''){
                this.$http.get("http://localhost:8080/users/search?username="+ this.searchText).then( result => {
                this.users = result.data;
                })
            }
        }
    }
}
</script>