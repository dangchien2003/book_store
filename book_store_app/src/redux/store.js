import { configureStore } from '@reduxjs/toolkit'
import managerFilterBookReducer from '@/features/manager/filterBook/filterBookSlice'
import managerClickFindBookReducer from '@/features/manager/filterBook/managerClickFindBookSlice'
export const store = configureStore({
  reducer: {
    managerFilterBook: managerFilterBookReducer,
    managerClickFindBook: managerClickFindBookReducer
  }
})