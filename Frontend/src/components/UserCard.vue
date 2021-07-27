<template>
  <v-card class="mx-auto mb-3" max-width="800" outlined shaped>
    <v-list-item three-line>
      <v-list-item-content>
        <div class="overline mb-4">{{ userParameter.userId }}</div>
        
        <v-list-item-title class="display-1 mb-6">
            {{ userParameter.username }}
        </v-list-item-title>
        <v-list-item-subtitle class="mb-3 mt-2">
          <span class="font-weight-bold">Name:</span>
          {{ userParameter.name }} {{userParameter.surname}}
        </v-list-item-subtitle>
        <v-list-item-subtitle class="mb-3 mt-2">
          <span class="font-weight-bold">Email:</span>
          {{ userParameter.email }}
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-card-actions>
        <router-link :to="`users/${userParameter.userId}`">
        <v-btn color="#f4f1ea">
            Edit
        </v-btn>
      </router-link>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
    name: "TagCard",
    data() {
        return {
        }
    },
    props: {
       userParameter: {
          type: Object,
          required: true
        }
    }
}
</script>