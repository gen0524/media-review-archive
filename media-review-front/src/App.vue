<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const reviews = ref([])
const title = ref('')
const category = ref('MOVIE')
const rating = ref(5)
const content = ref('')
const editingId = ref(null)

const loadReviews = async () => {
  try {
    const response = await axios.get(
      'http://localhost:8080/api/reviews'
    )

    reviews.value = response.data
  } catch (error) {
    console.error(error)
  }
}

const createReview = async () => {
  await axios.post('http://localhost:8080/api/reviews', {
    title: title.value,
    category: category.value,
    rating: rating.value,
    content: content.value
  })

  title.value = ''
  category.value = 'MOVIE'
  rating.value = 5
  content.value = ''

  loadReviews()
}

const deleteReview = async (id) => {
  if (!confirm('정말 삭제하시겠습니까?')) {
    return
  }

  try {
    await axios.delete(`http://localhost:8080/api/reviews/${id}`)
    await loadReviews()
  } catch (error) {
    console.error(error)
  }
}
const startEdit = (review) => {
  editingId.value = review.id
  title.value = review.title
  category.value = review.category
  rating.value = review.rating
  content.value = review.content
}
const updateReview = async () => {
  await axios.put(`http://localhost:8080/api/reviews/${editingId.value}`, {
    title: title.value,
    category: category.value,
    rating: rating.value,
    content: content.value
  })

  editingId.value = null
  title.value = ''
  category.value = 'MOVIE'
  rating.value = 5
  content.value = ''

  await loadReviews()
}

onMounted(() => {
  loadReviews()
})
</script>

<template>
  <h1>미디어 리뷰 아카이브</h1>

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

    <button v-if="editingId === null" @click="createReview">
      리뷰 등록
    </button>

    <button v-else @click="updateReview">
      리뷰 수정
    </button>
  </div>

  <div class="review-list">
    <div
        class="review-card"
        v-for="review in reviews"
        :key="review.id"
    >
      <h3>{{ review.title }}</h3>

      <p>
        <strong>카테고리</strong>
        {{ review.category }}
      </p>

      <p>
        <strong>별점</strong>
        ⭐ {{ review.rating }}
      </p>

      <p>{{ review.content }}</p>

      <button @click="startEdit(review)">
        수정
      </button>

      <button @click="deleteReview(review.id)">
        삭제
      </button>
    </div>
  </div>
</template>

<style>
body {
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
}

.review-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.review-card {
  background: white;
  padding: 16px;
  border-radius: 12px;

  box-shadow:
      0 2px 8px rgba(0,0,0,0.1);
}

button {
  margin-top: 10px;
  padding: 8px 12px;

  border: none;
  border-radius: 6px;

  cursor: pointer;
}
.form-box {
  background: white;
  padding: 16px;
  margin-bottom: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

input,
select,
textarea {
  display: block;
  width: 100%;
  margin-bottom: 10px;
  padding: 8px;
  box-sizing: border-box;
}
</style>