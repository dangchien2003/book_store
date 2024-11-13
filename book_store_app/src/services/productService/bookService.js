import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

async function getAllBook(page, filter) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllBook, {
    params: {
      page, name: filter.name, category: filter.category, publisher: filter.publisher, author: filter.author
    }
  })
}


export {
  getAllBook
}
