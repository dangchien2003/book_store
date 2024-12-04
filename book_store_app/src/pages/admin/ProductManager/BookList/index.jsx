import { Box } from '@mui/material'
import TableBook from './TableBook'
import Search from './Search'
import Filter from './Filter'

const BookList = () => {
  return (
    <Box sx={{
      height: '100%', display: {
        lg: 'flex',
        xs: 'block'
      }, gap: '10px'
    }} >
      <Filter />
      <Box flex={1}>
        <Search />
        <TableBook />
      </Box>
    </Box >
  )
}

export default BookList