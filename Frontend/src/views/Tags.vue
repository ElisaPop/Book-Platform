<template>
    <div>
        <!-- <input type="button" onclick="window.location.href = 'http://localhost:8081/tags/-1';" value="ADD"/> -->
        <!-- <b-table striped bordered hover :items="tags">
            <template v-slot:cell(tagName)="data">
                <router-link :to="`tags/${data.item.tagId}`">{{ data.value }}</router-link>
            </template>
        </b-table> -->
        <v-container>
        <v-tabs v-model="tab" background-color="#f4f1ea" max-width="800" centered>
            <v-text-field v-model="searchText" label="Search"></v-text-field>
            <v-tabs-slider></v-tabs-slider>
            <v-btn color="#3c2819" icon @click="search">
            <v-icon>mdi-magnify </v-icon>
            </v-btn>
            <v-btn color="#3c2819" icon onclick="window.location.href = 'http://localhost:8081/tags/-1';">
            <v-icon>mdi-plus-circle </v-icon>
            </v-btn>

            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Books' })"  >
                Books
            </v-btn>
            <v-btn color="#f4f1ea" >
                Tags
            </v-btn>
            <v-btn color="#3c2819" dark @click ="$router.push({ name: 'Users' })">
                Users
            </v-btn>
        
        </v-tabs>
        <v-tabs-items v-model="tab">
        <v-tab-item>
            <v-row no-gutters class="mt-4">
            <v-col cols="12" sm="3" v-for="tag in tags" :key="tag.tagId">
                <TagCard :tagParameter="tag" />
            </v-col>
            </v-row>
        </v-tab-item>
        </v-tabs-items>
        </v-container>

    </div>
</template>


<script>
import TagCard from "../components/TagCard";
export default {
    name: "Tags",
    components: {
        TagCard
    },
    props: ['currentTag'],
    data () {
        return {
            searchText: '',
            tab:0,
            tags:[]
        }
    },
   created(){
        this.$http.get("http://localhost:8080/tag").then( result => {
            this.tags = result.data;
        })
    },
    methods: {
        search(){
            if(this.searchText !== ''){
                this.$http.get("http://localhost:8080/tag/search?name="+ this.searchText).then( result => {
                this.tags = result.data;
                })
            }
        }
    }
}
</script>