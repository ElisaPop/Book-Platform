<template>
    <div>
        <!-- <h1> Tag # {{this.tag.tagId}}</h1> <br>  
        <template>
          <tr>
          <label for="tag-name"><b>Tag name:</b></label>
          <td><input id ="tag-name" v-model="message"></td>
          </tr>
          <br>
          <tr>
            <v-btn @click="save()"> SAVE</v-btn> 
            <td><v-btn @click="deleteTag()">DELETE</v-btn></td></tr>
         
        </template> -->
        <v-card
        class="mx-auto"
        max-width="600"
        flat
        tile>
        <v-container>
        <h2 v-if="isEdit"> Tag #{{this.tag.tagId}}</h2>
        <h2 v-if="isEdit === false"> Add tag</h2>
        <div id="app">
            <v-app id="inspire">
                <v-form
                ref="form"
                v-model="valid"
                lazy-validation
                >
                <v-text-field
                    v-model="tag.tagName"
                    :counter="20"
                    :rules="nameRules"
                    label="Tag Name"
                    required
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
                    @click="deleteTag"
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
            v => (v && v.length <= 20) || 'Name must be less than 20 characters',
            ],
            tag:{}
        }
    },
   created(){
       if(this.$route.params.id >= 0){
            this.isEdit = true;
            this.$http.get("http://localhost:8080/tag/" + this.$route.params.id).then( result => {
            this.tag = result.data;
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
               this.$http.put("http://localhost:8080/tag/", this.tag).then(result =>{
                    this.tag = result.data;
                })
            } else {
                 this.$http.post("http://localhost:8080/tag/", this.tag).then(result =>{
                    Object.assign(this.tag, result.data);
                })
            }
            this.$router.push({ name: 'Tags' })
        },
        editTag(tag){
            this.editedIndex = tag.tagId;
            this.editedTag = Object.assign({},tag);
        },
        deleteTag(){
            this.$http.delete("http://localhost:8080/tag/" + this.tag.tagId).then(()=>{this.$router.push("/tags")});
        }
    }
}
</script>
