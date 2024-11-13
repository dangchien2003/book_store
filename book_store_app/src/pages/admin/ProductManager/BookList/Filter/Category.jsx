import { getCategoryInPage } from '@/services/productService/categoryService'
import { toastError } from '@/utils/toast'
import { Autocomplete, TextField } from '@mui/material'
import { useEffect, useState } from 'react'

const Category = () => {
  const [selectedId, setSelectedId] = useState(null)
  const [categorys, setCategorys] = useState([])
  const [page, setPage] = useState(1)

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
          toastError('Có lỗi xảy ra')
        })
    }, 1500)
    return () => clearTimeout(timeoutId)
  }, [page, categorys])

  return (
    <Autocomplete
      disablePortal
      options={categorys}
      sx={{ mt: '15px', background: 'white' }}
      getOptionLabel={(option) => option.name}
      onChange={(event, newValue) => {
        setSelectedId(newValue ? newValue.id : null)
      }}
      renderInput={(params) => <TextField {...params} label="Danh mục" variant="outlined" />}
    />
  )
}

export default Category
