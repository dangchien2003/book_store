import { API_PRODUCT_SERVICE } from '@/configs/apiConfig'
import httpClient from '@/configs/axiosConfig'

async function getCategoryInPage(page) {
  return await httpClient.get(API_PRODUCT_SERVICE.getAllCategory, {
    params: {
      page
    }
  })
}


export {
  getCategoryInPage
}
