import 'package:flutter/material.dart';
import 'package:lab18/services/news_service.dart';

class AddNewsPage extends StatefulWidget {
  const AddNewsPage({Key? key}) : super(key: key);

  // final bool? isEditMode = false;
  @override
  State<AddNewsPage> createState() => _AddNewsPageState();
}

class _AddNewsPageState extends State<AddNewsPage> {
  String? _title;
  String? _text;
  NewsService newsService = NewsService();
  
  get isValid  {
    return _title != null && _title!.isNotEmpty && _text != null && _text!.isNotEmpty; 
  }
  
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
        appBar: AppBar(
          title: const Text("Add News"),
        ),
        body: Center(
            child: Container(
              constraints: const BoxConstraints(maxWidth: 400),
              child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
              children: [
                Text("Title", style: Theme.of(context).textTheme.headline6),
                TextField(
                  decoration: const InputDecoration(
                      border: UnderlineInputBorder(
                      ), hintText: "Title"),
                  onChanged: (value) {
                    setState(() {
                      _title = value;
                    });
                  },
                ),
                Container(
                  height: 24,
                ),

                Text("Text", style: Theme.of(context).textTheme.headline6),
                Container(
                  height: 24,
                ),
                TextField(
                    keyboardType: TextInputType.multiline,
                    maxLines: 4,
                    decoration: const InputDecoration( 
                        border: OutlineInputBorder(
                  ), hintText: "Text"),
                  onChanged: (value) {
                    setState(() {
                      _text = value;
                    });
                  },
                ),
                Container(
                  height: 24,
                ),
                ElevatedButton(
                  
                  onPressed: isValid ? () async {
                    var result = await newsService.addNews(_title!, _text!);
                    print(result);
                    Navigator.pop(context);
                  } : null,
                  child: const Padding(
                    padding: EdgeInsets.all(8.0),
                    child: Text('Add'),
                  ),
                )
              ],
          ),
        ),
            )));
  }
}
