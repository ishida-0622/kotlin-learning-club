package tic.kotlin.learning.club

import aws.sdk.kotlin.services.dynamodb.*
import aws.sdk.kotlin.services.dynamodb.endpoints.*
import aws.sdk.kotlin.services.dynamodb.model.*
import aws.sdk.kotlin.services.dynamodb.paginators.*
import aws.sdk.kotlin.services.dynamodb.waiters.*
import aws.sdk.kotlin.services.lambda.*
import java.util.UUID
import kotlin.reflect.*
import kotlin.reflect.full.*

// https://sdk.amazonaws.com/kotlin/api/latest/dynamodb/index.html
// https://docs.aws.amazon.com/ja_jp/sdk-for-kotlin/latest/developer-guide/kotlin_dynamodb_code_examples.html

/*

テーブル構成は以下の通りです

tableName: kotlin-learning

id(primarykey): String
name(sortkey): String
age: Int



以下のプログラムを作成します
User型のデータをDynamoに入れる際の型変換にはtoMapとtoAttributeValueMapを使用してください

1
  id = UUID
  name = 自分の名前
  age = 自分の年齢
  として、テーブルにput
  メソッド名はaddUserとします
  なお、id, name, ageは引数で受け取るようにしてください. User型で受け取っても構いません

2
  テーブルの中身を全件取得して表示
  メソッド名はscanAllとします

3
  id, nameが1で生成したid, nameのユーザーを検索して表示
  メソッド名はsearchByKeyとします
  なお、id, nameは引数で受け取るようにしてください

4
  ageが20歳以上のユーザーを検索して表示
  メソッド名はsearchByAgeとします
  なお、年齢は引数で受け取るようにしてください

以上の4つのメソッドをAppクラス内に作成し、Appクラスのhandlerメソッド内で順番に呼び出してください

*/

// ユーザーの型. id, nameは必須, ageは任意
data class User(val id: String, val name: String, val age: Int?)

// AWSのリージョン
val REGION = "ap-northeast-1"

// テーブル名
val TABLE_NAME = "kotlin-learning"

// インスタンス化
val utils = Utils()

class App {
  // リージョンを指定してDynamoDB clientを作成
  private val ddb_client = DynamoDbClient { region = REGION }

  suspend fun handler() {
    // ユーザーの情報を設定
    val id = UUID.randomUUID().toString()
    val name = "ishida"
    val age = 20
    val user = User(id, name, age)

    println("ユーザーを追加")
    addUser(user)
    println("追加完了\n")

    println("全件取得")
    scanAll()
    println("取得完了\n")

    println("id検索")
    searchByKey(id, name)
    println("検索完了\n")

    println("年齢で絞り込み")
    searchByAge(age)
    println("取得完了\n")
  }

  suspend fun addUser(user: User) {
    // ユーザーを追加
    utils.putItemInTable(TABLE_NAME, user)
  }

  suspend fun scanAll() {
    ddb_client.use { ddb ->
      // テーブル名を指定
      val request = ScanRequest { tableName = TABLE_NAME }
      // 全件取得
      val response = ddb.scan(request)
      // 取得結果を出力
      response.items?.forEach { item ->
        item.forEach { mp ->
          println("key = ${mp.key}")
          println("value = ${mp.value}")
        }
      }
    }
  }

  suspend fun searchByKey(id: String, name: String) {
    // idとnameをMapにセット
    val keys = mutableMapOf<String, Any>()
    keys["id"] = id
    keys["name"] = name

    // 取得
    val response = utils.getIetmWithKey(TABLE_NAME, keys)
    // 取得結果を出力
    response?.forEach { mp ->
      println("key = ${mp.key}")
      println("value = ${mp.value}")
    }
  }

  suspend fun searchByAge(age: Int) {
    ddb_client.use { ddb ->
      // テーブル名, 検索条件を指定
      val query = ScanRequest {
        tableName = TABLE_NAME
        filterExpression = "age >= :age"
        expressionAttributeValues = utils.toAttributeValueMap(mapOf(":age" to age))
      }
      // 取得
      val response = ddb.scan(query)
      // 取得結果を出力
      response.items?.forEach { item ->
        item.forEach { mp ->
          println("key = ${mp.key}")
          println("value = ${mp.value}")
        }
      }
    }
  }
}

suspend fun main() {
  // handlerを起動
  val app = App()
  app.handler()
}
