# Multi-Vendor E-Commerce Platform

### Problem Statement:

Single-vendor e-commerce sites are limited in scale and flexibility. To support multiple sellers and wider product variety, businesses require a multi-vendor architecture that includes vendor onboarding, global product catalog, vendor-specific product listings, and distributed order processing.

### Project Description:

This platform enables multiple vendors to list products based on a centralised catalog, customers to place orders from multiple vendors in one checkout, and admins to manage platform-wide settings. It includes order tracking, product reviews, and payment simulations. Admins can approve catalog products, vendor offers, manage user roles, and analyse platform performance.

## Roles: Admin, Vendor, Customer
The usernames and passwords for accessing Swagger UI are defined in the application's security configuration ([`SecurityConfig.java`](src/main/java/com/example/ecommerce/config/SecurityConfig.java:17)).
The available credentials are:

*   Username: `customer`, Password: `password`
*   Username: `vendor`, Password: `password`
*   Username: `admin`, Password: `password`

## API Endpoints

### 1. Authentication

```
POST /auth/register/customer – Register customer
POST /auth/register/vendor/apply – Apply as vendor
POST /auth/login – Login user
POST /auth/logout – Logout user
POST /auth/password/forgot – Request password reset
POST /auth/password/reset – Reset password
```

---

### 2. User Profile

```
GET /users/me – Get current user profile
PUT /users/me – Update current user profile
GET /users/me/addresses – List user addresses
POST /users/me/addresses – Add user address
PUT /users/me/addresses/{id} – Update address
DELETE /users/me/addresses/{id} – Delete address
```

---

### 3. Catalog Products (Admin-Managed)

```
GET /catalog-products – List all approved catalog products
GET /catalog-products/{id} – Get product details
POST /admin/catalog-products – Create catalog product
PUT /admin/catalog-products/{id} – Edit catalog product
DELETE /admin/catalog-products/{id} – Delete catalog product
```

### 4. Product Categories

```
GET /categories – List all product categories
POST /admin/categories – Create category
PUT /admin/categories/{id} – Edit category
DELETE /admin/categories/{id} – Delete category
```

---

### 5. Vendor Actions

- **Vendor Profile**

```
GET /vendor/profile – Get vendor store profile
PUT /vendor/profile – Update vendor store profile
```

- **Vendor Product Listings (Offers)**

```
POST /vendor/products – Add vendor offer for a catalog product
GET /vendor/products – List vendor products
GET /vendor/products/{productId} – View vendor product
PUT /vendor/products/{productId} – Update vendor product
DELETE /vendor/products/{productId} – Delete vendor product
```

- **Orders**

```
GET /vendor/orders – List vendor order items
GET /vendor/orders/items/{id} – Get order item details
PUT /vendor/orders/items/{id}/status – Update order item status
```

---

### 6. Customer Actions

- **Cart**

```
GET /customer/cart – View cart
POST /customer/cart/items – Add item to cart
PUT /customer/cart/items/{id} – Update cart item quantity
DELETE /customer/cart/items/{id} – Remove cart item
DELETE /customer/cart – Clear cart
```

- **Orders**

```
POST /customer/orders/checkout – Checkout and create order
GET /customer/orders – View order history
GET /customer/orders/{id} – View order details
POST /customer/orders/{id}/items/{id}/review – Submit product review
```

---

### 7. Admin Actions

- **Users**

```
GET /admin/users – List all users
GET /admin/users/{id} – Get user details
PUT /admin/users/{id}/role – Update user role
```

- **Vendor Applications**

```
GET /admin/vendor-applications – List vendor applications
PUT /admin/vendor-applications/{applicationId}/approve – Approve application
PUT /admin/vendor-applications/{applicationId}/reject – Reject application
```

- **Vendor Product Approval**

```
GET /admin/vendor-products/pending-approval – List vendor product offers pending approval
PUT /admin/vendor-products/{id}/approve –Approve vendor product
PUT /admin/vendor-products/{id}/reject – Reject vendor product
```

- **Orders & Reviews**

```
GET /admin/orders – List all orders
GET /admin/orders/{id} – Get order details
PUT /admin/orders/{id}/status – Update order status
DELETE /admin/reviews/{id} – Delete review
```

---

## Core Entities

### 1. User

- UserID (PK)
- FirstName
- LastName
- Email (Unique)
- PasswordHash
- Role (Enum: Customer, Vendor, Admin)
- PhoneNumber
- CreatedAt
- UpdatedAt

### 2. VendorProfile

- VendorID (PK)
- UserID (FK)
- StoreName
- StoreDescription
- BusinessAddress
- ContactNumber
- ApprovalStatus (Enum: Pending, Approved, Rejected)
- CreatedAt
- UpdatedAt

### 3. ProductCategory

- CategoryID (PK)
- Name
- Description
- CreatedAt
- UpdatedAt

### 4. ProductCatalog (Admin-Curated Product Info)

- CatalogProductID (PK)
- Name
- Brand
- Description
- GlobalSKU (Specific to Brand)
- CategoryID (FK)
- CreatedAt
- UpdatedAt

### 5. VendorProduct (Vendor Listing)

- VendorProductID (PK)
- CatalogProductID (FK)
- VendorID (FK)
- SKU (Unique per vendor)
- Price (Decimal)
- StockQuantity (Integer)
- ShippingAddress (FK)
- ApprovalStatus (Enum: Pending, Approved, Rejected)
- IsActive (Boolean - for vendor to quickly enable/disable)
- AverageRating (Decimal, calculated)
- CreatedAt
- UpdatedAt

### 6. Address

- AddressID (PK)
- UserID (FK)
- Street
- City
- State
- ZipCode
- Country
- AddressType (Shipping, Billing)

### 7. Order

- OrderID (PK)
- CustomerID (FK)
- OrderDate
- TotalAmount (Decimal - sum of all OrderItems)
- OverallOrderStatus (Enum: PendingPayment, Processing, PartiallyShipped, Shipped, Delivered, Cancelled, Refunded)
- ShippingAddressID (FK)
- BillingAddressID (FK)
- CreatedAt
- UpdatedAt

### 8. OrderItem

- OrderItemID (PK)
- OrderID (FK)
- VendorProductID (FK)
- Quantity (Integer)
- PriceAtPurchase
- Subtotal (Decimal - PriceAtPurchase \* Quantity)
- ItemStatus (Enum: Pending, Acknowledged, Preparing, Shipped, Delivered, CancelledByVendor, CancelledByCustomer)

### 9. Payment

- PaymentID (PK)
- OrderID (FK)
- PaymentDate
- Amount (Decimal)
- PaymentMethod (e.g., "Card", "UPI")
- TransactionID (Simulated)
- Status (Enum: Pending, Succeeded, Failed)

### 10. Review

- ReviewID (PK)
- VendorProductID (FK)
- CustomerID (FK)
- OrderID (FK)
- Rating (Integer, 1-5)
- Comment (Text)
- ReviewDate
- ApprovalStatus (Enum: Pending, Approved, Rejected)

### 11. ShoppingCart

- CartID (PK)
- CustomerID (FK)
- CreatedAt
- UpdatedAt

### 12. CartItem

- CartItemID (PK)
- CartID (FK)
- VendorProductID (FK)
- Quantity (Integer)
- AddedAt

---

## Key Relationships

| Entity A        | Relationship | Entity B                   | Comment                                                     |
| --------------- | ------------ | -------------------------- | ----------------------------------------------------------- |
| User            | 1:0..1       | VendorProfile              | A user may have a vendor profile (if they are a seller)     |
| User            | 1:N          | Orders                     | A customer can place many orders                            |
| User            | 1:N          | Reviews                    | A customer can write multiple product reviews               |
| User            | 1:N          | Addresses                  | A user can save multiple delivery/billing addresses         |
| User            | 1:0..1       | ShoppingCart               | A customer has at most one active shopping cart             |
| VendorProfile   | 1:N          | VendorProduct              | A vendor can list many products for sale                    |
| VendorProfile   | 1:N          | OrderItem                  | A vendor fulfills many items across different orders        |
| ProductCatalog  | 1:N          | VendorProduct              | Each catalog can have many vendor-specific product entries  |
| ProductCategory | 1:N          | ProductCatalog             | A category can group multiple product catalogs              |
| VendorProduct   | 1:N          | OrderItem                  | A product can appear in many order items                    |
| VendorProduct   | 1:N          | Review                     | A product can have many customer reviews                    |
| VendorProduct   | 1:N          | CartItem                   | A product can be in many customers’ carts                   |
| Order           | 1:N          | OrderItem                  | An order must contain at least one item                     |
| Order           | N:1          | User                       | Each order is placed by one customer                        |
| Order           | N:1          | Address (Shipping/Billing) | Each order has one shipping address                         |
| Order           | 1:N          | Payment                    | An order can have multiple payment attempts (e.g., retries) |
| ShoppingCart    | 1:N          | CartItem                   | A cart can contain multiple items from different products   |

## Other Features

1. Role Based Access Control
2. DTO-layer, Mapper, Validation

## Project Packages

1. Spring Boot
2. Swagger Documentation
3. MySQL
