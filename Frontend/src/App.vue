<template>
  <div id="app">
    <v-app id="inspire">
      <div>
        <v-tabs
          v-model="tab"
          background-color="#1d3557"
          class="elevation-2"
          dark
          :centered="centered"
          :grow="grow"
          :vertical="vertical"
          :right="right"
          :prev-icon="prevIcon ? 'mdi-arrow-left-bold-box-outline' : undefined"
          :next-icon="nextIcon ? 'mdi-arrow-right-bold-box-outline' : undefined"
          :icons-and-text="icons"
        >
          <h1 style="color:#a8dadc"> goofreads </h1>
          <v-tab v-if="this.$store.getters.userRole >= -1">
            <router-link :to="'/'"  style="color:#a8dadc">
              Home
            </router-link>
          </v-tab>
          <v-tab-item>
            <v-card
              flat
              tile
              >
              <router-view></router-view>
            </v-card>
          </v-tab-item>

          <v-tab v-if="this.$store.getters.userRole >= -1">
            <router-link :to="'/books'"  style="color:#a8dadc">
              Search
            </router-link>
          </v-tab>
          <v-tab-item>
            <v-card
              flat
              tile
              >
              <router-view></router-view>
            </v-card>
          </v-tab-item>

          <v-tab v-if="this.$store.getters.userRole >= 1">
            <router-link :to="'/ratings'"  style="color:#a8dadc">
              My Library
            </router-link>
          </v-tab>
          <v-tab-item>
            <v-card
              flat
              tile
              >
              <router-view></router-view>
            </v-card>
          </v-tab-item>

          <v-tab v-if="this.$store.getters.userRole >= 1">
            <router-link :to="'/profile'" style="color:#a8dadc">
              Profile
            </router-link>
          </v-tab>
          <v-tab-item>
            <v-card
              flat
              tile
              >
              <router-view></router-view>
            </v-card>
          </v-tab-item>

          <v-tab v-if="this.$store.getters.userRole <= 0">
            <router-link :to="'/login'"  style="color:#a8dadc">
              Log in
            </router-link>
          </v-tab>
          <v-tab-item>
            <v-card
              flat
              tile
              >
              <router-view></router-view>
            </v-card>
          </v-tab-item>

          <v-tab v-if="this.$store.getters.userRole > 0">
            <div style="color:#a8dadc" @click="logout()">
              Log out
            </div>
          </v-tab>
          <v-tab-item>
            <v-card
              flat
              tile
              >
              <router-view></router-view>
            </v-card>
          </v-tab-item>

        </v-tabs>
      </div>
    </v-app>
  </div>
</template>

  <!-- <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/books">Books</router-link> |
      <router-link to="/tags">Tags</router-link> |
    </div>
  </div> -->

<script>
import * as SockJS from 'sockjs-client'
import * as Stomp from 'stompjs'
export default {
    data () {
        return {
          tab: null,
          icons: false,
          centered: false,
          grow: false,
          vertical: false,
          prevIcon: false,
          nextIcon: false,
          right: false,
          tabs: 5,
        }
      },
      watch:{
        "$store.getters.userRole"(role){
          if(role > 0){
            this.isEdit = true;
              this.$http.get("http://localhost:8080/users/" + this.$store.getters.userData.userId).then( result => {
                  this.user = result.data;
              })
              this.topic = '/queue/distributor.' + this.$store.getters.userData.userId
              const ws = new SockJS('http://localhost:8080/ws')
              this.stompClient = Stomp.over(ws)
              const _this = this
              _this.stompClient.connect({}, function () {
                  
                  _this.stompClient.subscribe(_this.topic, function (event) {
                      alert(event.body)
                  })
              })
              console.log("Connected " + this.$store.getters.userData.userId)
        }
          else {
              this.$route.params.id = -1;
              this.isEdit = false;
          }
        }
    },
      methods: {
        logout(){
          this.stompClient.disconnect()
          this.$store.commit('setUserData',null);
          this.$store.commit('setUserRole',0);
          this.$router.push({ name: 'LogIn' })
        }
      }
    }
</script>
