package com.example.uijetpackcompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductApp()
        }
    }
}

@Composable
fun ProductApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(

            navController = navController,
            startDestination = "productList",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("productList") {
                ProductListScreen { productId ->
                    navController.navigate("productDetail/${Uri.encode(productId)}")

                }
            }
            composable("productDetail/{productId}") { backStackEntry ->
                val productId = Uri.decode(backStackEntry.arguments?.getString("productId") ?: "")
                if (productId.isNullOrEmpty()) {
                    // Xử lý lỗi hoặc điều hướng trở lại nếu productId không hợp lệ
                    navController.popBackStack()
                } else {
                    ProductDetailScreen(productId)
                }
            }


            composable("cart") {

            }
            composable("profile") {

            }
        }
    }
}

class CartViewModel : ViewModel() {
    // Danh sách sản phẩm trong giỏ hàng
    var cartItems = mutableStateListOf<Product>()
        private set

    // Thêm sản phẩm vào giỏ
    fun addToCart(product: Product) {
        cartItems.add(product)
    }

    // Xóa sản phẩm khỏi giỏ
    fun removeFromCart(product: Product) {
        cartItems.remove(product)
    }

    // Tính tổng giá trị giỏ hàng
    fun getTotalPrice(): String {
        val total = cartItems.sumOf { it.price.replace("$", "").toDouble() }
        return "$${"%.2f".format(total)}"
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", "productList", Icons.Default.Home),
        BottomNavItem("Cart", "cart", Icons.Default.ShoppingCart),
        BottomNavItem("Profile", "profile", Icons.Default.Person)
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)


@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Giỏ hàng", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        LazyColumn {
            items(cartViewModel.cartItems) { product ->
                Text(product.name)
            }
        }
        Text("Tổng tiền: ${cartViewModel.getTotalPrice()}")
    }
}

@Composable
fun ProductCard(
    product: Product,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product.name) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = "Product Image",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(product.description, fontSize = 12.sp, color = Color.Gray, maxLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBar(rating = product.rating)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        product.price,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF388E3C)
                    )
                }
            }
            IconButton(onClick = { onAddToCart(product) }) {
                Icon(
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = "Add to Cart",
                    tint = Color(0xFF000000)
                )
            }
        }
    }
}

data class Review(
    val text: String,
    val rating: Float,
    var likes: Int
)

@Composable
fun ProductDetailScreen(productId: String) {
    val cartViewModel: CartViewModel = viewModel()
    val mockProduct = Product(
        R.drawable.iphone11pro,
        "Chi tiết $productId",
        "THÔNG SỐ KỸ THUẬT\n" +
                "Màn hình:\t5.8\" Super Retina XDR\n" +
                "Camera trước:\t12 MP\n" +
                "Camera sau:\t3 camera 12 MP\n" +
                "Chipset:\tApple A13 Bionic 6 nhân (7nm+)\n" +
                "RAM:\t4 GB\n" +
                "Bộ nhớ trong:\t256GB\n" +
                "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 4G\n" +
                "Dung lượng pin:\t3190 mAh\n" +
                "Hệ điều hành:\tiOS 14",
        4.5f,
        "10.990.000 VNĐ"
    )

    // Tạo danh sách đánh giá với rating và lượt thích ảo
    val reviews = remember {
        mutableStateListOf(
            Review(
                "Sản phẩm tuyệt vời, chất lượng vượt mong đợi!",
                Random.nextFloat() * 2 + 3,
                Random.nextInt(0, 100)
            ),
            Review(
                "Giao hàng nhanh, đóng gói cẩn thận.",
                Random.nextFloat() * 2 + 3,
                Random.nextInt(0, 100)
            ),
            Review(
                "Giá cả hợp lý, sẽ mua lại lần sau.",
                Random.nextFloat() * 2 + 3,
                Random.nextInt(0, 100)
            ),
            Review(
                "Màu sắc đẹp và đúng như mô tả.",
                Random.nextFloat() * 2 + 3,
                Random.nextInt(0, 100)
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = mockProduct.imageRes),
            contentDescription = "Product Image",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(mockProduct.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(mockProduct.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(rating = mockProduct.rating)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            mockProduct.price,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF388E3C)
        )

        // Nút Add to Cart
        Button(
            onClick = { cartViewModel.addToCart(mockProduct) },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
        ) {
            Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to Cart")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add to Cart")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Đánh giá từ khách hàng", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // Hiển thị đánh giá ảo với rating và lượt thả tim
        reviews.forEach { review ->
            ReviewCard(review)
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    var likes by remember { mutableStateOf(review.likes) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = review.text, fontSize = 14.sp)
            RatingBar(rating = review.rating)

            // Hiển thị số lượt thả tim
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { likes++ }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "$likes lượt thích")
            }
        }
    }
}

@Composable
fun RatingBar(rating: Float, maxRating: Int = 5) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = if (i <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.3f
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = String.format("%.1f", rating))
    }
}

data class Product(
    val imageRes: Int,
    val name: String,
    val description: String,
    val rating: Float,
    val price: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(onProductClick: (String) -> Unit) {
    val cartViewModel = remember { CartViewModel() }
    val products = listOf(
        Product(
            R.drawable.iphone11pro,
            "iPhone 11 Pro 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\t5.8\" Super Retina XDR\n" +
                    "Camera trước:\t12 MP\n" +
                    "Camera sau:\t3 camera 12 MP\n" +
                    "Chipset:\tApple A13 Bionic 6 nhân (7nm+)\n" +
                    "RAM:\t4 GB\n" +
                    "Bộ nhớ trong:\t256GB\n" +
                    "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 4G\n" +
                    "Dung lượng pin:\t3190 mAh\n" +
                    "Hệ điều hành:\tiOS 14",
            4.5f,
            "10,990,000 VNĐ"
        ),
        Product(
            R.drawable.iphone16pro,
            "iPhone 16 Pro 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\tOLED 6.3\" Super Retina XDR, 2.000 nits\n" +
                    "Camera trước:\t12MP PDAF\n" +
                    "Camera sau:\tDuo 48MP (rộng + siêu rộng), 12Mp (tele)\n" +
                    "Chipset:\tApple A18 Pro\n" +
                    "RAM:\t8GB\n" +
                    "Bộ nhớ trong:\t256GB\n" +
                    "Thẻ sim:\tNano-SIM và eSIM\n" +
                    "Dung lượng pin:\t3.355 mAh\n" +
                    "Hệ điều hành:\tiOS 18",
            5.0f,
            "31,999,000 VNĐ"
        ),
        Product(
            R.drawable.iphone15promax,
            "iPhone 15 Pro Max 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\tOLED 6.7\" Super Retina XDR\n" +
                    "Camera trước:\t12 MP\n" +
                    "Camera sau:\t48 MP & 2 camera 12 MP\n" +
                    "Chipset:\tApple A17 Pro\n" +
                    "RAM:\t6 GB\n" +
                    "Bộ nhớ trong:\t256 GB\n" +
                    "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 5G\n" +
                    "Dung lượng pin:\t4852 mAh, 20W\n" +
                    "Hệ điều hành:\tIOS 17",
            4.5f,
            "28,780,000 VNĐ"
        ),
        Product(
            R.drawable.iphone14promax,
            "iPhone 14 PRO MAX 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\tOLED 6.7\" Super Retina XDR\n" +
                    "Camera trước:\t12 MP\n" +
                    "Camera sau:\t48 MP & 2 camera 12 MP\n" +
                    "Chipset:\tApple A16 Bionic\n" +
                    "RAM:\t6 GB\n" +
                    "Bộ nhớ trong:\t256GB\n" +
                    "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 5G\n" +
                    "Dung lượng pin:\tChưa xác định\n" +
                    "Hệ điều hành:\tiOS 16",
            3.5f,
            "18,880,000 VNĐ"
        ),
        Product(
            R.drawable.iphone11pro,
            "iPhone 11 Pro 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\t5.8\" Super Retina XDR\n" +
                    "Camera trước:\t12 MP\n" +
                    "Camera sau:\t3 camera 12 MP\n" +
                    "Chipset:\tApple A13 Bionic 6 nhân (7nm+)\n" +
                    "RAM:\t4 GB\n" +
                    "Bộ nhớ trong:\t256GB\n" +
                    "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 4G\n" +
                    "Dung lượng pin:\t3190 mAh\n" +
                    "Hệ điều hành:\tiOS 14",
            4.5f,
            "10,990,000 VNĐ"
        ),
        Product(
            R.drawable.iphone16pro,
            "iPhone 16 Pro 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\tOLED 6.3\" Super Retina XDR, 2.000 nits\n" +
                    "Camera trước:\t12MP PDAF\n" +
                    "Camera sau:\tDuo 48MP (rộng + siêu rộng), 12Mp (tele)\n" +
                    "Chipset:\tApple A18 Pro\n" +
                    "RAM:\t8GB\n" +
                    "Bộ nhớ trong:\t256GB\n" +
                    "Thẻ sim:\tNano-SIM và eSIM\n" +
                    "Dung lượng pin:\t3.355 mAh\n" +
                    "Hệ điều hành:\tiOS 18",
            5.0f,
            "31,999,000 VNĐ"
        ),
        Product(
            R.drawable.iphone14promax,
            "iPhone 14 PRO MAX 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\tOLED 6.7\" Super Retina XDR\n" +
                    "Camera trước:\t12 MP\n" +
                    "Camera sau:\t48 MP & 2 camera 12 MP\n" +
                    "Chipset:\tApple A16 Bionic\n" +
                    "RAM:\t6 GB\n" +
                    "Bộ nhớ trong:\t256GB\n" +
                    "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 5G\n" +
                    "Dung lượng pin:\tChưa xác định\n" +
                    "Hệ điều hành:\tiOS 16",
            3.5f,
            "18,880,000 VNĐ"
        ),
        Product(
            R.drawable.iphone15promax,
            "iPhone 15 Pro Max 256GB (VN/A)",
            "THÔNG SỐ KỸ THUẬT\n" +
                    "Màn hình:\tOLED 6.7\" Super Retina XDR\n" +
                    "Camera trước:\t12 MP\n" +
                    "Camera sau:\t48 MP & 2 camera 12 MP\n" +
                    "Chipset:\tApple A17 Pro\n" +
                    "RAM:\t6 GB\n" +
                    "Bộ nhớ trong:\t256 GB\n" +
                    "Thẻ sim:\t1 Nano SIM & 1 eSIM, Hỗ trợ 5G\n" +
                    "Dung lượng pin:\t4852 mAh, 20W\n" +
                    "Hệ điều hành:\tIOS 17",
            4.5f,
            "28,780,000 VNĐ"
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hoàng Apple") },
                navigationIcon = {
                    IconButton(onClick = { /* Xử lý khi nhấn nút quay lại */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Filter action */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                }
            )
        }

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
           
            items(products) { product ->
                ProductCard(
                    product = product,
                    onProductClick = onProductClick,
                    onAddToCart = { cartViewModel.addToCart(it) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Composable
fun RatingBar(rating: Float) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating.toInt()) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Product List Screen Preview")
@Composable
fun PreviewProductListScreen() {
    ProductListScreen(onProductClick = {})
}

@Preview(showBackground = true, name = "Product List Screen Click Preview")
@Composable
fun PreviewProductListScreenWithClick() {
    ProductListScreen(onProductClick = { /* Xử lý khi click */ })
}

@Preview(showBackground = true, name = "Full App Preview")
@Composable
fun PreviewProductApp() {
    ProductApp()
}
