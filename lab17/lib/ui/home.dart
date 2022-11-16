import 'package:flutter/material.dart';
import 'package:flutter/material.dart';
class HelloCard extends StatelessWidget {
  const HelloCard({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("HelloCard"),
      ),
      body: Container(
        alignment: Alignment.center,
        child: Stack(
          alignment: Alignment.center,
          children: <Widget>[
            _getCard(),
            _getAvatar(),
          ],
        ),
      ),
    );
  }
}

Container _getCard() {
  return Container(
    width:350,
    height: 200,
    decoration: BoxDecoration(
      color: Colors.blueAccent,
      borderRadius: BorderRadius.circular(26),
    ),
    child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        const Text("Hello, Students",
          style: TextStyle(fontSize: 24,
              color:Colors.greenAccent),),
        const Text("Heppy New Year!!!!"),
        Row(
          mainAxisAlignment:
          MainAxisAlignment.center,
          children: const [
            Icon(Icons.two_k_rounded),
            Icon(Icons.ac_unit),
            Icon(Icons.two_k_rounded),
            Icon(Icons.two_k_rounded),
          ],
        )
      ],
    ),
  );
}

Container _getAvatar(){
  return Container(
    margin: const EdgeInsets.only(bottom: 200),
    width: 100,
    height: 100,
    decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: const BorderRadius.all(Radius.circular(50.0)),
        border: Border.all(color:Colors.orange,width: 1.2),
        image: const DecorationImage(image: NetworkImage("https://picsum.photos/300/300"),
            fit:BoxFit.cover)
    ),
  );
}
