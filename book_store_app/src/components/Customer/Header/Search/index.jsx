import { SearchOutlined } from "@mui/icons-material"
import { Box, Button } from "@mui/material"

const Search = () => {
  return (
    <Box sx={{
      maxHeight: '70px',
      padding: '5px 0'
    }}>
      <Box sx={{
        height: '40px',
        border: '1px solid #f0f0f0',
        display: 'flex',
        justifyContent: 'space-between',
        borderRadius: '10px',
        padding: '4px'
      }}>
        <input type="text" placeholder="Bạn muốn mua sách gì?"
          style={{ width: '85%', border: 'none', outline: 'none', fontSize: '16px', padding: '0 8px' }} />
        <Button variant="contained" sx={{ background: '#fe763e', width: '15%' }}>
          <SearchOutlined />
        </Button>
      </Box>
    </Box>
  )
}

export default Search
