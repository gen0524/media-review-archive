<script setup>
defineProps({
  editingId: Number
})

const title = defineModel('title')
const category = defineModel('category')
const rating = defineModel('rating')
const content = defineModel('content')

defineEmits([
  'create',
  'update'
])
</script>

<template>
  <div class="form-box">
    <h2>{{ editingId === null ? '리뷰 작성' : '리뷰 수정' }}</h2>

    <input v-model="title" placeholder="제목 입력" />

    <select v-model="category">
      <option value="MOVIE">영화</option>
      <option value="DRAMA">드라마</option>
      <option value="ANIMATION">애니</option>
      <option value="TV_SHOW">예능</option>
      <option value="YOUTUBE">유튜브</option>
      <option value="BOOK">책</option>
    </select>

    <input v-model="rating" type="number" min="1" max="5" />

    <textarea v-model="content" placeholder="감상평 입력"></textarea>

    <button v-if="editingId === null" @click="$emit('create')">
      리뷰 등록
    </button>

    <button v-else @click="$emit('update')">
      리뷰 수정
    </button>
  </div>
</template>