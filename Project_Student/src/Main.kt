import java.util.*
import kotlin.random.Random

fun main(){
    val list:List<Student> = List()
    list.add(Student(1,"Иванов Михаил Игоревич", arrayOf(4,5,3,8,9)))
    list.add(Student(2, "Глассов Вячеслав Дмитриевич", arrayOf(3,8,9,7,8)))
    list.add(Student(3, "Чаранко Дмитрий Анатольевич", arrayOf(9,9,8,7,8)))
    list.add(Student(4, "Сайтамов Родион Родионович", arrayOf(3,4,5,2,9)))
    list.add(Student(5,"Геносов Генадий Викторович", arrayOf(9,9,5,3,6)))
    list.add(Student(6, "Кингов Иосиф Генадиевич", arrayOf(3,10,3,2,9)))
menu(list)
}
fun menu(list: List<Student>) {
    print("Вы препод.\n У вас есть 6 всевозможных(не очень) функций использовать эту программу\n" +
            "1)Вывести список всех студентов и их среднего баллов после сессии\n" +
            "2)Вывести список студентов на отчисление(3 оценки, ниже 4)\n" +
            "3)Вывести самого успешного по оценкам студента\n" +
            "4)Вывести самого большого неудачника в группе\n" +
            "Чтобы выйти из меню нажмите 0\n" +
            "Удачки:3\n")
    do {
        print("Выберите подходящий пункт в меню: ")

        val option = checkNumberForCorrectInput()

        fun outputAverageMarkOfEachStudent() {
         var i = 0
            while (i<list.size()){
                val number = list.get(i).number
                val name = list.get(i).fullName
                val averageMark = list.get(i).averageMark
                println("Номер: $number, ФИО: $name, Средняя оценка: $averageMark")
                i++
            }
        }

        fun outputListForDeduction() {
            fun isStudentGoToDeduction(mas: Array<Int>): Boolean {
                var amountOfMarkBelowMinimum = 0
                for (i in mas)
                    if (i < 4)
                        amountOfMarkBelowMinimum++
                return (amountOfMarkBelowMinimum >= 3)
            }
            var i = 0
            println("Студенты на отчисление")
            while (i < list.size()) {
                if(isStudentGoToDeduction(list.get(i).arrayOfMarks)) {
                    val number = list.get(i).number
                    val name = list.get(i).fullName
                    println("Номер: $number, ФИО: $name")
                }
                i++
            }

        }

        fun outputTheMostSuccessfulStudent() {
            var i = 0
            var sumOfMarks = list.get(i).countFullSumOfMarks()
            var indexOfSuccessfulStudent = 0
            while(i<list.size()){
                if(sumOfMarks<list.get(i).countFullSumOfMarks()) {
                    sumOfMarks = list.get(i).countFullSumOfMarks()
                    indexOfSuccessfulStudent = list.get(i).number
                }
                i++
            }
            println("Самый успешный студент: ${list.get(indexOfSuccessfulStudent-1).fullName}" +
                    " средный балл: ${list.get(indexOfSuccessfulStudent-1).averageMark}")
        }

        fun outputTheMostHopelessStudent() {
            var i = 0
            var sumOfMarks = list.get(i).countFullSumOfMarks()
            var indexOfSuccessfulStudent = 0
            while(i<list.size()){
                if(sumOfMarks>list.get(i).countFullSumOfMarks()) {
                    sumOfMarks = list.get(i).countFullSumOfMarks()
                    indexOfSuccessfulStudent = list.get(i).number
                }
                i++
            }
            println("Самый неудачный студент: ${list.get(indexOfSuccessfulStudent-1).fullName}" +
                    " средный балл: ${list.get(indexOfSuccessfulStudent-1).averageMark}")
        }
        when (option) {
            1 -> outputAverageMarkOfEachStudent()
            2 -> outputListForDeduction()
            3 -> outputTheMostSuccessfulStudent()
            4 -> outputTheMostHopelessStudent()
        }
        if(option == 0)
            print("Понял, выключаюсь)\n")
    }while(option!=0)
}
    fun checkNumberForCorrectInput():Int{
        val option:Int
        var stringNumber:String
        val regex = Regex("\\d+")
        val scanner = Scanner(System.`in`)
        do {
            stringNumber = scanner.nextLine()
            if(stringNumber.matches(regex)){
                option = stringNumber.toInt()
                break
            }else println("Неправильный ввод, повторите")
        }while (true)
        return option
    }
class Student(number:Int, fullName:String, arrayOfMarks:Array<Int>) {
    var number: Int = 0
        set(value) {
            if (field < 0)
                field = Random.nextInt(0, 100)
            field = value
        }
    var fullName: String = "Ivanov Ivan Ivanovich"
        get() {
            if (field.split(" ").size != 3)
                field = "Ivanov Ivan Ivanovich"
            return field
        }
    var arrayOfMarks: Array<Int> = Array(4) { 0 }
    var averageMark: Int = 0

    init {
        this.number = number
        this.fullName = fullName
        this.arrayOfMarks = arrayOfMarks
        this.averageMark = average(arrayOfMarks)
    }

    private fun average(mas: Array<Int>): Int {
        var averageMark = 0
        for (mark in mas) {
            averageMark += mark
        }
        return averageMark / mas.size
    }
    fun countFullSumOfMarks(mas:Array<Int> = arrayOfMarks):Int{
        var sum = 0
        for (mark in mas) {
            sum += mark
        }
        return sum
    }
}
class List<E> {
    private var root: Node<E>? = Node(null, null)
    private var size: Int = 0
    init {
        root = Node(null, null)
    }

    fun add(elem: E) {
        val newElem: Node<E> = Node(null, elem)
        var copyRoot = root
        var i = 0
        while (i < size) {
            copyRoot = copyRoot!!.next
            i++
        }
        copyRoot!!.next = newElem
        size++
    }

    fun get(index:Int): E {
        var elem = root!!.next
        var i = 0
        while(i<index){
            elem = elem!!.next
            i++
        }
        return elem!!.curr!!
    }

    fun size(): Int {
        return size
    }
    fun isEmpty():Boolean{
        return size==0
    }

    fun remove(index:Int){
        var i = 0
        var rootCopy = root!!.next!!
        while(i<index-1){
            rootCopy = rootCopy.next!!
            i++
        }
        rootCopy.setNextNode(rootCopy.next!!.next!!)
        size--
    }

    fun set(index:Int, elem: E){
        var i = 0
        var rootCopy = root!!.next!!
        while(i<index){
            rootCopy = rootCopy.next!!
            i++
        }
        rootCopy.curr = elem
    }
    class Node<E>(var next: Node<E>?, var curr: E?){
        fun setNextNode(next:Node<E>?){
            this.next = next
        }
    }
}

