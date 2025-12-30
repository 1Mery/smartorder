<h1>SmartOrder — Microservice E-Commerce (WIP)</h1>

<p>
  SmartOrder; mikroservis mimarisi ile kurgulanmış, sipariş/ürün/müşteri süreçlerini ve event tabanlı bildirim akışını gösteren
  bir e-ticaret demo projesidir. Proje <strong>geliştirme aşamasındadır (Work in Progress)</strong>;
  amaç, okunabilir katmanlı yapı + servisler arası iletişim (sync + async) örneklerini tek repoda sergilemektir.
</p>

<hr />

<h2>İçindekiler</h2>
<ul>
  <li><a href="#mimari-genel-bakis">Mimari Genel Bakış</a></li>
  <li><a href="#servisler">Servisler</a></li>
  <li><a href="#proje-yapisi">Proje Yapısı</a></li>
  
</ul>

<hr />

<h2 id="mimari-genel-bakis">Mimari Genel Bakış</h2>

<h3>Sistem Diyagramı</h3>
<pre>
+-----------+            +-------------------+            +------------------+
| Client/UI | -- HTTP -->|   Gateway Server  | -- LB -->  | Eureka Discovery |
+-----------+            +-------------------+            +------------------+
                                 |
                                 | routes
         +-----------------------+------------------------+
         |                        |                        |
         v                        v                        v
+------------------+      +------------------+      +------------------+
| customer-service |      | product-service  |      |  order-service   |
+------------------+      +------------------+      +------------------+
         |                        |                        |
         v                        v                        v
  +--------------+         +--------------+         +-------------------+
  | Postgres     |         | Postgres     |         | Postgres          |
  | (customer)   |         | (product)    |         | (order + outbox)  |
  +--------------+         +--------------+         +-------------------+
                                                        |
                                                        | publish events
                                                        v
                                                  +-------------+
                                                  |   Kafka     |
                                                  +-------------+
                                                        |
                                                        | consume events
                                                        v
                                              +----------------------+
                                              | notification-service  |
                                              +----------------------+

Config Server:
+-----------------+
|  config-server  |
+-----------------+
  | provides configuration to: gateway, eureka, all services
</pre>

<h3>Neden Bu Yapı?</h3>
<ul>
  <li><strong>Gateway</strong>: Dışarıdan tek giriş noktası; servisleri gizler ve yönlendirir.</li>
  <li><strong>Discovery (Eureka)</strong>: Servislerin birbirini dinamik olarak bulmasını sağlar.</li>
  <li><strong>Config Server</strong>: Konfigürasyonları merkezi yönetir.</li>
  <li><strong>Kafka</strong>: Sipariş yaşam döngüsü event’leri ile gevşek bağlı entegrasyon sağlar.</li>
  <li><strong>Outbox (order-service)</strong>: Event publish işlemini veriye bağlayarak tutarlılığı güçlendirir.</li>
</ul>

<hr />

<h2 id="servisler">Servisler</h2>

<table>
  <thead>
    <tr>
      <th align="left">Servis</th>
      <th align="left">Sorumluluk</th>
      
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>customer-service</strong></td>
      <td>Müşteri oluşturma, get-by-id, silme, müşteri doğrulama (verify)</td>
      
    </tr>
    <tr>
      <td><strong>product-service</strong></td>
      <td>Ürün yönetimi + stok/price güncellemeleri + order için product-info</td>
      
    </tr>
    <tr>
      <td><strong>order-service</strong></td>
      <td>Sipariş oluşturma ve durum geçişleri; outbox ile event yayınlama</td>
      
    </tr>
    <tr>
      <td><strong>notification-service</strong></td>
      <td>Kafka event tüketip Notification domain’i üzerinden bildirim üretimi</td>
    
    </tr>
    <tr>
      <td><strong>gateway-server</strong></td>
      <td>API Gateway (routing + load balancing)</td>
      <td>config</td>
    </tr>
    <tr>
      <td><strong>discovery-server</strong></td>
      <td>Eureka registry</td>
      <td>-</td>
    </tr>
    <tr>
      <td><strong>config-server</strong></td>
      <td>Merkezi konfigürasyon</td>
      <td>-</td>
    </tr>
  </tbody>
</table>

<hr />



<h2 id="proje-yapisi">Proje Yapısı</h2>

<p>Repo; altyapı servisleri + iş servisleri şeklinde düzenlenmiştir:</p>

<ul>
  <li><strong>Infra</strong>
    <ul>
      <li>config-server</li>
      <li>discovery-server</li>
      <li>gateway-server</li>
    </ul>
  </li>
  <li><strong>Business</strong>
    <ul>
      <li>customer-service</li>
      <li>product-service</li>
      <li>order-service</li>
      <li>notification-service</li>
    </ul>
  </li>
</ul>

<h2>Hakkında</h2>
<p>
  Bu repo bir <strong>demo + öğrenme + portföy</strong> projesi olarak geliştirilmektedir.
  Hedef; mikroservislerde <strong>katmanlı mimari</strong>, <strong>port-adapter yaklaşımı</strong>,
  <strong>event-driven iletişim</strong> ve <strong>outbox</strong> gibi desenleri anlaşılır şekilde göstermek.
</p>
