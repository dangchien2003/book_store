import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

export async function getAllBook(page, filter) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllBook, {
    params: {
      page, name: filter.name, category: filter.category, publisher: filter.publisher, author: filter.author
    }
  })
}

export async function getBookDetail(id) {
  return await httpClient.get(API_PRODUCT_SERVICE.bookDetail + id)
}

export async function updateBookDetail(detail) {
  return await httpClient.patch(API_PRODUCT_SERVICE.updateBookDetail, detail)
}

export async function uploadImage(file, type, bookId) {
  const data = new FormData()
  data.append('file', file)
  data.append('type', type)
  data.append('bookId', bookId)
  return await httpClient.post(API_PRODUCT_SERVICE.uploadImage, data, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export async function removeImage(bookId, type, index) {
  return await httpClient.put(API_PRODUCT_SERVICE.removeImage, {
    bookId: bookId,
    type: type,
    index: index
  })
}

