package com.wafflestudio.seminar.spring2023.kotlin

fun main() {
    println("\n--------------\n")
    객체()
    println("\n--------------\n")
    변수()
    println("\n--------------\n")
    표현식()
    println("\n--------------\n")
    수신객체()
    println("\n--------------\n")
    분기()
    println("\n--------------\n")
    `컬렉션-리스트`()
    println("\n--------------\n")
    `컬렉션-맵`()
    println("\n--------------\n")
    `컬렉션-익스텐션`()
    println("\n--------------\n")
    `컬렉션-이터레이션`()
    println("\n--------------\n")
}

fun 객체() {
    val userExplicit: DemoUser = DemoUser("기본 생성")

    val userImplicit = DemoUser("타입 추론")

    println("객체 생성\n$userExplicit")
}

fun 변수() {
    val userNotNullable = DemoUser("널 불가")

    var userNullable: DemoUser? = null

    userNullable = DemoUser("널 가능")

    println("변수\n$userNullable")
}

fun 표현식() {
    val name = if (true) {
        "와플"
    } else {
        "스튜디오"
    }

    println("표현식\n$name")
}

fun 수신객체() {
    var userNullable: DemoUser? = null

    // print 호출 X
    userNullable?.also { println(it) }
    userNullable?.let { println(it) }
    userNullable?.apply { println(this) }
    userNullable?.run { println(this) }

    userNullable = DemoUser("수신 객체 NOT NULL")

    // print 호출 O
    println("수신객체")
    userNullable?.also { println(it) }
    userNullable?.let { println(it) }
    userNullable?.apply { println(this) }
    userNullable?.run { println(this) }
}

fun 분기() {
    val name = "와플"

    // print C
    println("분기")
    when (name) {
        "스튜디오" -> println("A")
        "크러플" -> println("B")
        "와플" -> println("C")
        else -> println("D")
    }
}

fun `컬렉션-리스트`() {
    val empty: List<DemoUser> = listOf()

    val explicit: List<DemoUser> = listOf(DemoUser("A"), DemoUser("B"))

    val implicit = listOf(DemoUser("A"), DemoUser("B"))

    val mutableEmpty: MutableList<DemoUser> = mutableListOf()

    val mutableExplicit: List<DemoUser> = mutableListOf(DemoUser("A"), DemoUser("B"))

    val mutableImplicit = mutableListOf(DemoUser("A"), DemoUser("B"))

    println("컬렉션 리스트\n$explicit")
}

fun `컬렉션-맵`() {
    val empty: Map<String, DemoUser> = mapOf()

    val explicit: Map<String, DemoUser> = mapOf("A" to DemoUser("A"))

    val implicit = mapOf("A" to DemoUser("A"))

    val mutableEmpty: MutableMap<String, DemoUser> = mutableMapOf()

    val mutableExplicit: MutableMap<String, DemoUser> = mutableMapOf("A" to DemoUser("A"))

    val mutableImplicit = mutableMapOf("A" to DemoUser("A"))

    println("컬렉션 맵\n$explicit")
}

fun `컬렉션-익스텐션`() {
    val users = listOf(DemoUser("A"), DemoUser("B"))

    // filter
    val usersFiltered = users.filter { it.username != "A" }

    // map
    val usersX2 = users.map { DemoUser("${it.username}${it.username}") }

    // 오름차순 sort
    val usersSorted1 = users.sortedBy { it.username }
    // 내림차순 sort
    val usersSorted2 = users.sortedByDescending { it.username }

    // list to map
    val usersMap: Map<String, DemoUser> = users.associate { it.username to it } // key 지정, value 지정
    val usersMap2: Map<String, DemoUser> = users.associateBy { it.username } // key 지정
    val usersMap3: Map<DemoUser, String> = users.associateWith { it.username } // value 지정

    println("컬렉션-익스텐션\n$usersFiltered\n$usersX2\n$usersSorted1")
}

fun `컬렉션-이터레이션`() {
    val users = listOf(DemoUser("A"), DemoUser("B"))

    println("컬렉션-이터레이션")

    users.forEach { user ->
        println(user)
    }

    for (user in users) {
        println(user)
    }
}

private data class DemoUser(val username: String)
