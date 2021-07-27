<template>
    <div>
        <v-card
        class="mx-auto"
        max-width="600"
        flat
        tile>
        <v-container>
            <h2 v-if="isEdit"> Book #{{this.book.bookId}}</h2>
            <h2 v-if="isEdit === false"> Add book</h2>
            <div id="app">
                <v-app id="inspire">
                    <v-form
                    ref="form"
                    v-model="valid"
                    lazy-validation
                    >
                    <v-text-field
                        v-model="book.title"
                        :counter="100"
                        :rules="nameRules"
                        label="Book Title"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="book.authors"
                        :counter="100"
                        :rules="nameRules"
                        label="Authors"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="book.publicationYear"
                        label="Publication Year"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="book.authorId"
                        label="Author ID"
                    ></v-text-field>
                    <v-text-field
                        v-model="book.photo"
                        label="Photo"
                    ></v-text-field>

                    <v-btn
                        v-if="isEdit === true"
                        color="#f4f1ea"
                        @click="save"
                    >
                        Edit
                    </v-btn>
                    <v-btn
                        v-else
                        color="#f4f1ea"
                        @click="save"
                    >
                        Save
                    </v-btn>

                    <v-btn
                        v-if="isEdit === true"
                        color="#f4f1ea"
                        @click="deleteBook"
                    >
                        Delete
                    </v-btn>
                    </v-form>
                </v-app>
                </div>
            </v-container>
        </v-card>
    </div>
</template>

<script>

export default {
    data: function () {
        return {
            isEdit: true,
            valid: true,
            nameRules: [
            v => !!v || 'Name is required',
            v => (v && v.length <= 100) || 'Name must be less than 100 characters',
            ],
            book:{}
        }
    },
   created(){
       if(this.$route.params.id >= 0){
            this.isEdit = true;
            this.$http.get("http://localhost:8080/book/" + this.$route.params.id).then( result => {
             this.book = result.data;
        })
       }
        else {
            this.$route.params.id = -1;
            this.isEdit = false;
        }
    },
    methods: {
        save(){
            if(this.isEdit){
               this.$http.put("http://localhost:8080/book/", this.book).then(result =>{
                    this.book = result.data;
                })
            } else {
                 this.$http.post("http://localhost:8080/book/", this.book).then(result =>{
                    Object.assign(this.book, result.data);
                })
            }
            this.$router.push({ name: 'Books' })
        },
        deleteBook(){
            this.$http.delete("http://localhost:8080/book/" + this.book.bookId).then(()=>{this.$router.push("/books")});
        }
    }
}
</script>
