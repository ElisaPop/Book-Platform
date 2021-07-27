<template>
  <v-card class="mx-auto mb-3" max-width="800" outlined shaped>
    <v-list-item three-line>
      <v-list-item-content>
        <div class="overline mb-4">{{ tagParameter.tagId }}</div>
        
        <v-list-item-title class="display-1 mb-6">
           
            {{ tagParameter.tagName }}
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>
    <v-card-actions>
        <router-link :to="`tags/${tagParameter.tagId}`">
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
       tagParameter: {
          type: Object,
          required: true
        }
    }
}
</script>