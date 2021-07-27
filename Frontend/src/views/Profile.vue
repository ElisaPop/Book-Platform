<template>
    <div>
        <v-card
        class="mx-auto"
        max-width="600"
        flat
        tile>
        <v-container>
            <h2 v-if="isEdit"> {{this.user.username}}</h2>
            <div id="app">
                <v-app id="inspire">
                    <v-form
                    ref="form"
                    v-model="valid"
                    lazy-validation
                    >
                    <v-text-field
                        v-model="user.name"
                        :counter="20"
                        :rules="nameRules"
                        label="Name"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="user.surname"
                        :counter="20"
                        :rules="nameRules"
                        label="Surname"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="user.email"
                        :rules="emailRules"
                        label="E-mail"
                        required
                    ></v-text-field>

                    <v-btn
                        v-if="isEdit === true"
                        color="#f4f1ea"
                        @click="save"
                    >
                        Edit
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
            stompClient: null,
            nameRules: [
            v => !!v || 'Name is required',
            v => (v && v.length <= 20) || 'Name must be less than 100 characters',
            ],
             emailRules: [
            v => !!v || 'E-mail is required',
            v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
            ],
            user:{}
        }
    },
   
    methods: {
        save(){
            if(this.isEdit){
               this.$http.put("http://localhost:8080/users/", this.user).then(result =>{
                    this.user = result.data;
                })
            }
            this.$router.push({ name: 'Users' })
        }
    }
}
</script>
