import { incrementByFilter } from '@/features/manager/filterBook/filterBookSlice'
import { getCategoryInPage } from '@/services/productService/categoryService'
import { Autocomplete, TextField } from '@mui/material'
import { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux'

const Category = () => {
  const [categorys, setCategorys] = useState([])
  const [page, setPage] = useState(1)
  const dispatch = useDispatch()

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      getCategoryInPage(page)
        .then(response => {
          if (response.data.result.length === 0) {
            return
          }
          setCategorys(categorys.concat(response.data.result))
          setPage(currentPage => currentPage + 1)
        })
        .catch(() => {
        })
    }, 1500)
    return () => clearTimeout(timeoutId)
  }, [page, categorys])

  const handleChangeValue = (event, newValue) => {
    const value = newValue ? newValue.id : null
    dispatch(incrementByFilter({ field: 'category', value: value }))
  }

  return (
    <Autocomplete
      disablePortal
      options={categorys}
      sx={{ mt: '15px', background: 'white' }}
      getOptionLabel={(option) => option.name}
      onChange={handleChangeValue}
      renderInput={(params) => <TextField {...params} label="Danh mục" variant="outlined" />}
    />
  )
}

export default Category
