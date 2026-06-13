<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import AppHeader from '../components/AppHeader.vue'
import AppFooter from '../components/AppFooter.vue'
import ReviewForm from '../components/ReviewForm.vue'
import ReviewCard from '../components/ReviewCard.vue'
import LoginBox from '../components/LoginBox.vue'
import { useUserStore } from '../stores/userStore'

const reviews = ref([])

const title = ref('')
const category = ref('MOVIE')
const rating = ref(5)
const content = ref('')
const status = ref('COMPLETED')
const watchedDate = ref('')
const shortReview = ref('')
const userStore = useUserStore()

const editingId = ref(null)
const searchKeyword = ref('')
const selectedCategory = ref('ALL')
const selectedSort = ref('LATEST')
const selectedStatus = ref('ALL')

const resetForm = () => {
  editingId.value = null
  title.value = ''
  category.value = 'MOVIE'
  rating.value = 5
  content.value = ''
  status.value = 'COMPLETED'
  watchedDate.value = ''
  shortReview.value = ''
}

const loadReviews = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/reviews')
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
    content: content.value,
    status: status.value,
    watchedDate: watchedDate.value || null,
    shortReview: shortReview.value,
    username: userStore.username
  })

  resetForm()
  await loadReviews()
}

const deleteReview = async (id) => {
  if (!confirm('정말 삭제하시겠습니까?')) return

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
  status.value = review.status || 'COMPLETED'
  watchedDate.value = review.watchedDate || ''
  shortReview.value = review.shortReview || ''
}

const updateReview = async () => {
  await axios.put(`http://localhost:8080/api/reviews/${editingId.value}`, {
    title: title.value,
    category: category.value,
    rating: rating.value,
    content: content.value,
    status: status.value,
    watchedDate: watchedDate.value || null,
    shortReview: shortReview.value
  })

  resetForm()
  await loadReviews()
}

const filteredReviews = computed(() => {
  const filtered = reviews.value.filter(review => {
    const title = review.title || ''
    const category = review.category || ''

    const matchesKeyword = title
        .toLowerCase()
        .includes(searchKeyword.value.toLowerCase())

    const matchesCategory =
        selectedCategory.value === 'ALL' ||
        category === selectedCategory.value ||
        category === getCategoryLabel(selectedCategory.value)

    const matchesStatus =
        selectedStatus.value === 'ALL' ||
        review.status === selectedStatus.value

    return matchesKeyword && matchesCategory && matchesStatus
  })

  return filtered.sort((a, b) => {
    if (selectedSort.value === 'RATING_DESC') {
      return b.rating - a.rating
    }

    if (selectedSort.value === 'RATING_ASC') {
      return a.rating - b.rating
    }

    return new Date(b.createdAt) - new Date(a.createdAt)
  })
})

const totalReviews = computed(() => reviews.value.length)

const averageRating = computed(() => {
  if (reviews.value.length === 0) return 0

  const sum = reviews.value.reduce(
      (acc, review) => acc + review.rating,
      0
  )

  return (sum / reviews.value.length).toFixed(1)
})

const completedCount = computed(() =>
    reviews.value.filter(
        review => review.status === 'COMPLETED'
    ).length
)

const watchingCount = computed(() =>
    reviews.value.filter(
        review => review.status === 'WATCHING'
    ).length
)

const wantToWatchCount = computed(() =>
    reviews.value.filter(
        review => review.status === 'WANT_TO_WATCH'
    ).length
)

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

    <RouterLink to="/want-to-watch">
      <button>보고 싶은 목록</button>
    </RouterLink>

    <RouterLink to="/completed">
      <button>감상 완료 목록</button>
    </RouterLink>

    <div class="stats-box">
      <h2>리뷰 통계</h2>

      <p>총 리뷰 수 : {{ totalReviews }}개</p>

      <p>평균 별점 : {{ averageRating }}점</p>

      <p>감상 완료 : {{ completedCount }}개</p>

      <p>보는 중 : {{ watchingCount }}개</p>

      <p>보고 싶음 : {{ wantToWatchCount }}개</p>
    </div>

    <ReviewForm
        v-model:title="title"
        v-model:category="category"
        v-model:rating="rating"
        v-model:content="content"
        v-model:status="status"
        v-model:watchedDate="watchedDate"
        v-model:shortReview="shortReview"
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

    <select v-model="selectedStatus" class="filter-select">
      <option value="ALL">전체 상태</option>
      <option value="WANT_TO_WATCH">보고 싶음</option>
      <option value="WATCHING">보는 중</option>
      <option value="COMPLETED">감상 완료</option>
    </select>

    <select v-model="selectedSort" class="filter-select">
      <option value="LATEST">최신순</option>
      <option value="RATING_DESC">별점 높은순</option>
      <option value="RATING_ASC">별점 낮은순</option>
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
.stats-box {
  background: white;
  padding: 16px;
  margin-bottom: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

</style>