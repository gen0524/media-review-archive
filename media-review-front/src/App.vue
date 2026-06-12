<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import AppHeader from './components/AppHeader.vue'
import AppFooter from './components/AppFooter.vue'
import ReviewCard from './components/ReviewCard.vue'
import ReviewForm from './components/ReviewForm.vue'
import LoginBox from './components/LoginBox.vue'

const reviews = ref([])
const title = ref('')
const category = ref('MOVIE')
const rating = ref(5)
const content = ref('')
const editingId = ref(null)
const searchKeyword = ref('')
const selectedCategory = ref('ALL')

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
const filteredReviews = computed(() => {
  return reviews.value.filter(review => {
    const title = review.title || ''
    const category = review.category || ''

    const matchesKeyword = title
        .toLowerCase()
        .includes(searchKeyword.value.toLowerCase())

    const matchesCategory =
        selectedCategory.value === 'ALL' ||
        category === selectedCategory.value ||
        category === getCategoryLabel(selectedCategory.value)

    return matchesKeyword && matchesCategory
  })
})

const getCategoryLabel = (category) => {
  const labels = {
    MOVIE: '영화',
    DRAMA: '드라마',
    ANIMATION: '애니',
    TV_SHOW: '예능',
    YOUTUBE: '유튜브',
    BOOK: '책'
  }

  return labels[category] || category
}

onMounted(() => {
  loadReviews()
})
</script>

<template>
  <div class="container">
    <AppHeader />
    <LoginBox />

    <ReviewForm
        v-model:title="title"
        v-model:category="category"
        v-model:rating="rating"
        v-model:content="content"
        :editing-id="editingId"
        @create="createReview"
        @update="updateReview"
    />

    <input
        v-model="searchKeyword"
        class="search-input"
        placeholder="제목으로 검색"
    />

    <select v-model="selectedCategory" class="filter-select">
      <option value="ALL">전체</option>
      <option value="MOVIE">영화</option>
      <option value="DRAMA">드라마</option>
      <option value="ANIMATION">애니</option>
      <option value="TV_SHOW">예능</option>
      <option value="YOUTUBE">유튜브</option>
      <option value="BOOK">책</option>
    </select>

    <div class="review-list">
      <ReviewCard
          v-for="review in filteredReviews"
          :key="review.id"
          :review="review"
          @edit="startEdit"
          @delete="deleteReview"
      />
    </div>

    <AppFooter />
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
.search-input {
  margin-bottom: 20px;
  padding: 12px;
  font-size: 16px;
}
.filter-select {
  margin-bottom: 20px;
  padding: 12px;
  font-size: 16px;
}
.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 30px;
}

h1 {
  text-align: center;
  margin-bottom: 24px;
}

.form-box h2 {
  text-align: center;
}

.review-card {
  text-align: left;
}

.review-card h3 {
  font-size: 24px;
  margin-bottom: 12px;
}

button {
  margin-right: 8px;
  background-color: #eeeeee;
}

button:hover {
  background-color: #dddddd;
}
.delete-button {
  background-color: #ffdddd;
}

.delete-button:hover {
  background-color: #ffbbbb;
}
</style>