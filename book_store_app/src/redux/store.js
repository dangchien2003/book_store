import { configureStore } from '@reduxjs/toolkit'
import managerFilterBookReducer from '@/features/manager/filterBook/filterBookSlice'
import managerClickFindBookReducer from '@/features/manager/filterBook/managerClickFindBookSlice'
import authorReducer from '@/features/author/authorSlice'
import publisherReducer from '@/features/publisher/publisherSlice'
import categoryReducer from '@/features/category/categorySlice'

export const store = configureStore({
  reducer: {
    managerFilterBook: managerFilterBookReducer,
    managerClickFindBook: managerClickFindBookReducer,
    author: authorReducer,
    publisher: publisherReducer,
    category: categoryReducer
  }
})